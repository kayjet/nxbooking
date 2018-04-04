package com.booking.common.service.impl;

import com.booking.common.base.Constants;
import com.booking.common.dto.ProductDto;
import com.booking.common.dto.ProductListDto;
import com.booking.common.entity.*;
import com.booking.common.mapper.*;
import com.booking.common.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * OrderServiceImpl
 *
 * @author kai.liu
 * @date 2018/01/02
 */
@Service
public class OrderServiceImpl implements IOrderService {
    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    OrderShopRelMapper orderShopRelMapper;

    @Autowired
    OrderUserRelMapper orderUserRelMapper;

    @Autowired
    OrderProductRelMapper orderProductRelMapper;

    @Override
    public List<OrderEntity> listAll() {
        return orderMapper.selectList(null);
    }

    @Override
    public List<OrderEntity> listOrder(OrderEntity orderEntity) {
        return orderMapper.selectList(orderEntity);
    }

    @Override
    public Page<List<OrderEntity>> listOrderPage(OrderEntity order, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize = orderMapper.likeCount(order);
        Page<List<OrderEntity>> page = new Page<List<OrderEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<OrderEntity> result = orderMapper.selectLikeList(order);
        page.setResult(result);
        return page;
    }

    @Override
    public OrderEntity getOrder(String id) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(id);
        return orderMapper.selectOne(orderEntity);
    }

    @Override
    public int addOrder(OrderEntity orderEntity) {
        orderEntity.setId(UUID.randomUUID().toString());
        return orderMapper.insert(orderEntity);
    }

    @Override
    public int addOrder(List<OrderEntity> orderEntitys) {
        int result = 0;
        for (OrderEntity entity : orderEntitys) {
            result += orderMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeOrder(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(id);
        return orderMapper.delete(orderEntity);
    }

    @Override
    public int removeOrder(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if (StringUtils.isEmpty(id)) {
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setId(id);
            result += orderMapper.delete(orderEntity);
        }
        return result;
    }

    @Override
    public int updateById(OrderEntity orderEntity, String id) {
        OrderEntity where = new OrderEntity();
        where.setId(id);
        return this.update(orderEntity, where);
    }

    @Override
    public int update(OrderEntity orderEntity, OrderEntity where) {
        return orderMapper.update(orderEntity, where);
    }


    @Override
    public OrderEntity makeOrder(String shopId, String userId, String concatPhone, String totalPrice, String orderType,
                                 String orderTime, List<List<ProductEntity>> products) {
        OrderEntity orderEntity = new OrderEntity();
        String orderId = UUID.randomUUID().toString();
        String orderNo = "WX" + System.currentTimeMillis();
        orderEntity.setId(orderId);
        orderEntity.setOrderNo(orderNo);
        orderEntity.setOrderStatus(Constants.OrderStatus.WAITING_PAY);
        orderEntity.setOrderType(orderType);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        if (orderType.equals(Constants.OrderType.NOW)) {
//            Date date = new Date(System.currentTimeMillis() + 60 * 1000 * 5);
//            orderTime = dateFormat.format(date);
//        }
        orderEntity.setOrderTime(orderTime);
        orderEntity.setConcatPhone(concatPhone);
        orderEntity.setTotalPriceFromWeb(Double.valueOf(totalPrice));
        Double price = 0D;
        for (List<ProductEntity> productEntities : products) {
            for (ProductEntity entity : productEntities) {
                ProductEntity p = productMapper.selectOne(new ProductEntity(entity.getId()));
                price += p.getPrice();
                insertOrderProductRel(p.getId(), orderId);
            }
        }
        orderEntity.setTotalPrice(price);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        orderEntity.setCreateTime(ts);
        orderEntity.setUpdateTime(ts);
        orderMapper.insert(orderEntity);
        insertOrderUserRel(userId, orderId);
        insertOrderShopRel(shopId, orderId);
        return orderEntity;
    }

    @Override
    public ProductListDto getOrderProductList(String orderId) {
        ProductListDto productListDto = new ProductListDto();
        OrderProductRelEntity query = new OrderProductRelEntity();
        query.setOrderId(orderId);
        List<OrderProductRelEntity> result = orderProductRelMapper.selectList(query);
        Map<String, ProductDto> map = new HashMap<String, ProductDto>();
        if (!CollectionUtils.isEmpty(result)) {
            for (OrderProductRelEntity entity : result) {
                for (ProductEntity productEntity : entity.getProductList()) {
                    if (map.containsKey(productEntity.getId())) {
                        ProductDto dto = map.get(productEntity.getId());
                        dto.setSum(dto.getSum() + 1);
                        dto.setPrice(dto.getPrice() + productEntity.getPrice());
                    } else {
                        ProductDto dto = new ProductDto(productEntity);
                        dto.setSum(1);
                        map.put(productEntity.getId(), dto);
                    }
                }
            }
        }
        List<ProductDto> ret = new ArrayList<ProductDto>();
        ret.addAll(map.values());
        productListDto.setProductDtos(ret);
        int size = 0;
        for (ProductDto dto : ret){
            size += dto.getSum();
        }
        productListDto.setSum(size);
        return productListDto;
    }

    private void insertOrderUserRel(String userId, String orderId) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String uid = UUID.randomUUID().toString();
        OrderUserRelEntity orderUserRelEntity = new OrderUserRelEntity();
        orderUserRelEntity.setId(uid);
        orderUserRelEntity.setCreateTime(ts);
        orderUserRelEntity.setUpdateTime(ts);
        orderUserRelEntity.setUserId(userId);
        orderUserRelEntity.setOrderId(orderId);
        orderUserRelMapper.insert(orderUserRelEntity);
    }

    private void insertOrderProductRel(String productId, String orderId) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String uid = UUID.randomUUID().toString();
        OrderProductRelEntity orderProductRelEntity = new OrderProductRelEntity();
        orderProductRelEntity.setId(uid);
        orderProductRelEntity.setCreateTime(ts);
        orderProductRelEntity.setUpdateTime(ts);
        orderProductRelEntity.setProductId(productId);
        orderProductRelEntity.setOrderId(orderId);
        orderProductRelMapper.insert(orderProductRelEntity);
    }

    private void insertOrderShopRel(String shopId, String orderId) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String uid = UUID.randomUUID().toString();
        OrderShopRelEntity orderShopRelEntity = new OrderShopRelEntity();
        orderShopRelEntity.setId(uid);
        orderShopRelEntity.setCreateTime(ts);
        orderShopRelEntity.setUpdateTime(ts);
        orderShopRelEntity.setShopId(shopId);
        orderShopRelEntity.setOrderId(orderId);
        orderShopRelMapper.insert(orderShopRelEntity);
    }
}
