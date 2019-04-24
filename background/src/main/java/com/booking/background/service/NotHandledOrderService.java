package com.booking.background.service;

import com.booking.background.utils.WSOrderMangager;
import com.booking.background.websocket.WSHandlerController;
import com.booking.common.dto.OrderDetailDto;
import com.booking.common.entity.OrderDetailEntity;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.ShopEntity;
import com.booking.common.mapper.OrderMapper;
import com.booking.common.mapper.OrderShopRelMapper;
import com.booking.common.service.IShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * NotHandledOrderService
 *
 * @author kai.liu
 * @date 2018/05/08
 */
@Service
public class NotHandledOrderService {
    Logger logger = LoggerFactory.getLogger(NotHandledOrderService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void getAllShopNotHandledOrderList() {
        logger.info("getAllShopNotHandledOrderList START");
        String selectAllShopSql = "SELECT * FROM booking.t_shop";
        List<ShopEntity> shops = jdbcTemplate.query(selectAllShopSql, new Object[]{}, new BeanPropertyRowMapper(ShopEntity.class));
        for (ShopEntity shop : shops) {
            String shopId = shop.getId();
            String selectOrderListPushedButNotHandled =
                    String.format(" SELECT rel.shop_id,ord.* FROM `t_order_shop_rel` rel LEFT JOIN t_order ord ON ord.id = rel.order_id\n" +
                            "            WHERE ord.is_handler = 1 AND ord.is_pushed = 2 AND rel.shop_id = '%s'", shopId);
            List<OrderEntity> pushedButNotHandledOrderList =
                    jdbcTemplate.query(selectOrderListPushedButNotHandled, new Object[]{}, new BeanPropertyRowMapper(OrderEntity.class));
            if (!CollectionUtils.isEmpty(pushedButNotHandledOrderList)) {
                setOrderDetail(pushedButNotHandledOrderList);
                WSOrderMangager.addOrder(shopId, pushedButNotHandledOrderList);
            }

        }
        logger.info("getAllShopNotHandledOrderList END");
    }

    private void setOrderDetail(OrderEntity order) {
        String selectOrderDetailList =
                String.format("  SELECT\n" +
                        "        T.*,spec.name as spec_name\n" +
                        "        FROM\n" +
                        "        (\n" +
                        "        SELECT DISTINCT\n" +
                        "        ord.id AS order_id,\n" +
                        "        ord.order_no,\n" +
                        "        order_product_rel.id AS order_product_rel_id,\n" +
                        "        product.title AS product_name,\n" +
                        "        product.id as product_id\n" +
                        "        FROM\n" +
                        "        t_order ord\n" +
                        "        LEFT JOIN t_order_product_rel order_product_rel ON order_product_rel.order_id = ord.id\n" +
                        "        LEFT JOIN t_product product ON order_product_rel.product_id = product.id\n" +
                        "        ORDER BY ord.order_no\n" +
                        "        ) T\n" +
                        "        LEFT JOIN t_order_product_spec_rel spec_rel ON spec_rel.order_product_rel_id = T.order_product_rel_id\n" +
                        "        LEFT JOIN t_product_spec spec ON spec.id = spec_rel.spec_id\n" +
                        "        WHERE  T.order_no = '%s'", order.getOrderNo());
        List<OrderDetailEntity> orderDetailEntities = jdbcTemplate.query(selectOrderDetailList, new Object[]{}, new BeanPropertyRowMapper(OrderDetailEntity.class));
        List<OrderDetailDto> reuslt = new ArrayList<OrderDetailDto>();
        for (OrderDetailEntity detailEntity : orderDetailEntities) {
            OrderDetailDto dto = new OrderDetailDto(detailEntity);
            if (reuslt.contains(dto)) {
                if (!StringUtils.isEmpty(detailEntity.getSpecName())) {
                    reuslt.get(reuslt.indexOf(dto)).getProductSpecList().add(detailEntity.getSpecName());
                }
            } else {
                reuslt.add(dto);
            }
        }
        order.setOrderDetailList(reuslt);
    }

    private void setOrderDetail(List<OrderEntity> orderList) {
        for (OrderEntity orderEntity : orderList) {
            setOrderDetail(orderEntity);
        }

    }
}
