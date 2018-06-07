package com.booking.background.service;

import com.booking.background.utils.WSOrderMangager;
import com.booking.background.utils.WSSessionManager;
import com.booking.background.utils.WebsocketUtils;
import com.booking.background.websocket.WSHandlerController;
import com.booking.common.base.Constants;
import com.booking.common.dto.OrderDetailDto;
import com.booking.common.entity.OrderDetailEntity;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.OrderShopRelEntity;
import com.booking.common.entity.WechatPayCallbackEntity;
import com.booking.common.mapper.OrderMapper;
import com.booking.common.mapper.OrderShopRelMapper;
import com.booking.common.utils.NetTool;
import com.booking.common.utils.WxPayUtil;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opdar.platform.core.base.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * OrderCallbackService
 *
 * @author kai.liu
 * @date 2018/04/19
 */
@Service
public class OrderCallbackService {

    Logger logger = LoggerFactory.getLogger(OrderCallbackService.class);


    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderShopRelMapper orderShopRelMapper;

    public WechatPayCallbackEntity readCallbackXmlFromWechat() {
        String xml = null;
        try {
            xml = new String(NetTool.read(Context.getRequest().getInputStream()), "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (xml == null) {
            return null;
        }

        logger.info("---------------------XML from wx callback -----------------");
        logger.info(xml);
        logger.info("--------------------XML from wx callback -----------------");

        WechatPayCallbackEntity wechatPayCallbackEntity = null;
        try {
            XmlMapper mapper = new XmlMapper();
            wechatPayCallbackEntity = mapper.readValue(xml, WechatPayCallbackEntity.class);
        } catch (IOException e) {
            logger.error("Jackson xml 解析出错");
            logger.error(e.getMessage());
            logger.error("Jackson xml 解析出错");
        }
        return wechatPayCallbackEntity;
    }

    public boolean handleAndSendMessage(String transactionId,String outTradeNno) {
        try {
            OrderEntity query = new OrderEntity();
            query.setTransactionId(transactionId);
            query.setOrderNo(outTradeNno);
            OrderEntity ret = orderMapper.selectOne(query);
            setOrderDetail(ret);
            if (ret.getIsPushed().equals(Constants.OrderPushStatus.NOT_PUSH)) {
                OrderShopRelEntity orderShopRel = new OrderShopRelEntity();
                orderShopRel.setOrderId(ret.getId());
                orderShopRel = orderShopRelMapper.selectOne(orderShopRel);
                if (orderShopRel == null || StringUtils.isEmpty(orderShopRel.getShopId())) {
                    return false;
                }
                String shopId = orderShopRel.getShopId();
                WebSocketSession webSocketSession = WSSessionManager.getSession(shopId);
                if (webSocketSession != null) {
                    try {
                        WebsocketUtils.sendHeartBeat(webSocketSession, WebsocketUtils.WS_SUCCESS_CODE, ret);
                        ret.setIsPushed(Constants.OrderPushStatus.PUSHED);
                    } catch (IOException e) {
                        WSOrderMangager.handleOrderOnException(ret, shopId);
                        e.printStackTrace();
                    }
                    orderMapper.updatePushStatusWithLock(ret);
                    return true;
                } else {
                    WSOrderMangager.handleOrder(ret, shopId);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }


    private void setOrderDetail(OrderEntity order) {
        List<OrderDetailEntity> orderDetailEntities = orderMapper.selectOrderDetailList(order.getOrderNo());
        List<OrderDetailDto> result = new ArrayList<OrderDetailDto>();

        for (OrderDetailEntity detailEntity : orderDetailEntities) {
            OrderDetailDto dto = new OrderDetailDto(detailEntity);
            if (result.contains(dto)) {
                if (!StringUtils.isEmpty(detailEntity.getSpecName())) {
                    result.get(result.indexOf(dto)).getProductSpecList().add(detailEntity.getSpecName());
                }
            } else {
                result.add(dto);
            }
        }
        order.setOrderDetailList(result);
    }
}
