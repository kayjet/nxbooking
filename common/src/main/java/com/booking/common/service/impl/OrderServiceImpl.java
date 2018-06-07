package com.booking.common.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.booking.common.base.Constants;
import com.booking.common.dto.ProductDto;
import com.booking.common.dto.ProductListDto;
import com.booking.common.dto.ProductSpecDto;
import com.booking.common.entity.*;
import com.booking.common.mapper.*;
import com.booking.common.service.IOrderService;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    ShopMapper shopMapper;

    @Value("${wechat.isdebug}")
    private boolean wechatIsDebug;

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


    @Autowired
    private OrderProductSpecRelMapper orderProductSpecRelMapper;

    @Override
    public OrderEntity makeOrder(String shopId, String userId, String concatPhone, String totalPrice, String orderType,
                                 String orderTime, List<List<ProductEntity>> products) {
        logger.info("创建订单 start");
        OrderEntity orderEntity = new OrderEntity();
        String orderId = UUID.randomUUID().toString();
        String orderNo = "WX" + System.currentTimeMillis();
        orderEntity.setId(orderId);
        orderEntity.setOrderNo(orderNo);
        orderEntity.setOrderStatus(Constants.OrderStatus.WAITING_PAY);
        orderEntity.setOrderType(orderType);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(orderType) && orderType.equals(Constants.OrderType.APPOINT) && !StringUtils.isEmpty(orderTime)) {
            Date now = new Date(System.currentTimeMillis());
            orderTime = dateFormat.format(now) + " " + orderTime + ":00";
            orderEntity.setOrderTime(orderTime);
        }
        orderEntity.setConcatPhone(concatPhone);
        orderEntity.setTotalPriceFromWeb(Double.valueOf(totalPrice));
        Double price = 0D;
        for (int parentIdx = 0; parentIdx < products.size(); parentIdx++) {

            List<ProductEntity> productEntities = products.get(parentIdx);
            for (int index = 0; index < productEntities.size(); index++) {
                ProductEntity entity = productEntities.get(index);
                ProductEntity p = productMapper.selectOne(new ProductEntity(entity.getId()));
                price += p.getPrice();
                String orderProductRelId = insertOrderProductRel(p.getId(), orderId);
                List<ProductSpecDto> productSpecDtoList = entity.getRequestSpecList();
                if (!CollectionUtils.isEmpty(productSpecDtoList)) {
                    for (ProductSpecDto specDto : productSpecDtoList) {
                        List<ProductSpecEntity> productSpecList = specDto.getSpecList();
                        if (!CollectionUtils.isEmpty(productSpecList)) {
                            for (ProductSpecEntity specEntity : productSpecList) {
                                if (specEntity.getPrice() != null) {
                                    price += specEntity.getPrice();
                                }
                                String pid = p.getId();
                                String specId = specEntity.getId();
                                Timestamp ts = new Timestamp(System.currentTimeMillis());
                                OrderProductSpecRelEntity specRelEntity = new OrderProductSpecRelEntity();
                                specRelEntity.setCreateTime(ts);
                                specRelEntity.setUpdateTime(ts);
                                specRelEntity.setId(UUID.randomUUID().toString());
                                specRelEntity.setSpecId(specId);
                                specRelEntity.setOrderProductRelId(orderProductRelId);
                                orderProductSpecRelMapper.insert(specRelEntity);
                            }
                        }
                    }
                }
            }
        }
        orderEntity.setTotalPrice(price);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        orderEntity.setCreateTime(ts);
        orderEntity.setUpdateTime(ts);
        orderMapper.insert(orderEntity);
        insertOrderUserRel(userId, orderId);
        insertOrderShopRel(shopId, orderId);
        logger.info("创建订单 end");
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
        for (ProductDto dto : ret) {
            size += dto.getSum();
        }
        productListDto.setSum(size);
        return productListDto;
    }

    @Override
    public void updatePayStatus(String orderNo, String transactionId) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNo(orderNo);
        OrderEntity ret = orderMapper.selectOne(orderEntity);
        if (ret != null && ret.getOrderStatus().equals(Constants.OrderStatus.WAITING_PAY)) {
            ret.setOrderStatus(Constants.OrderStatus.PAID);
            ret.setTransactionId(transactionId);
            orderMapper.updatePayStatusWithLock(ret);
        }
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Workbook exportExcel(OrderEntity orderEntity) {
        String shopId = orderEntity.getShopId();
        ShopEntity shopResult = shopMapper.selectOne(new ShopEntity(shopId));
        String dateRange = "";

        OrderEntity query = new OrderEntity();
        if (orderEntity.getCreateTimeStart() != null && orderEntity.getCreateTimeEnd() != null) {
            query.setCreateTimeStart(orderEntity.getCreateTimeStart());
            query.setCreateTimeEnd(orderEntity.getCreateTimeEnd());
            dateRange = simpleDateFormat.format(orderEntity.getCreateTimeStart()) + "到"
                    + simpleDateFormat.format(orderEntity.getCreateTimeEnd());
        }

        query.setOrderStatus(Constants.OrderStatus.PAID);
        List<OrderEntity> orderList = orderMapper.selectList(query);
        if (CollectionUtils.isEmpty(orderList)) {
            return null;
        }
        for (OrderEntity entity : orderList) {
            entity.setFee(shopResult.getPayRate());
            entity.setMustPay((shopResult.getPayRate() * entity.getTotalPrice()) / 100D);
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(shopResult.getName() + " " + dateRange + "订单记录", "详情"),
                OrderEntity.class, orderList);
        return workbook;
    }

    @Override
    public boolean validateOrderPrice(String orderNo, Double price) {
        if(wechatIsDebug){
            return true;
        }
        OrderEntity query = new OrderEntity();
        query.setOrderNo(orderNo);
        OrderEntity order = orderMapper.selectOne(query);
        if (price != null && order != null && order.getTotalPrice().equals(price)) {
            return true;
        }
        return false;
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

    private String insertOrderProductRel(String productId, String orderId) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String uid = UUID.randomUUID().toString();
        OrderProductRelEntity orderProductRelEntity = new OrderProductRelEntity();
        orderProductRelEntity.setId(uid);
        orderProductRelEntity.setCreateTime(ts);
        orderProductRelEntity.setUpdateTime(ts);
        orderProductRelEntity.setProductId(productId);
        orderProductRelEntity.setOrderId(orderId);
        orderProductRelMapper.insert(orderProductRelEntity);
        return uid;
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
