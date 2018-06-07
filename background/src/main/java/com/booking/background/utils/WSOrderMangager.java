package com.booking.background.utils;

import com.booking.background.websocket.WSHandlerController;
import com.booking.common.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * WSOrderMangager
 *
 * @author kai.liu
 * @date 2018/06/06
 */
public class WSOrderMangager {
    private static final  Logger logger = LoggerFactory.getLogger(WSOrderMangager.class);
    private static final ConcurrentMap<String, ConcurrentLinkedQueue<OrderEntity>> ORDER_QUEUE_CONCURRENT_MAP =
            new ConcurrentHashMap<String, ConcurrentLinkedQueue<OrderEntity>>();

    public static void addOrder(String shopId, Collection<OrderEntity> pushedButNotHandledOrderList) {
        if (ORDER_QUEUE_CONCURRENT_MAP.containsKey(shopId)) {
            ConcurrentLinkedQueue<OrderEntity> queue = ORDER_QUEUE_CONCURRENT_MAP.get(shopId);
            for (OrderEntity orderEntity : pushedButNotHandledOrderList) {
                if (!queue.contains(orderEntity)) {
                    queue.add(orderEntity);
                }
            }
        } else {
            ConcurrentLinkedQueue<OrderEntity> concurrentLinkedQueue = new ConcurrentLinkedQueue<OrderEntity>();
            concurrentLinkedQueue.addAll(pushedButNotHandledOrderList);
            ORDER_QUEUE_CONCURRENT_MAP.put(shopId, concurrentLinkedQueue);
        }
    }

    public static ConcurrentLinkedQueue<OrderEntity> getOrderQueue(String shopId) {
        ConcurrentLinkedQueue<OrderEntity> resultData = null;
        if (ORDER_QUEUE_CONCURRENT_MAP.containsKey(shopId)) {
            resultData = ORDER_QUEUE_CONCURRENT_MAP.get(shopId);
            logger.info("ORDER_QUEUE_CONCURRENT_MAP = " + ORDER_QUEUE_CONCURRENT_MAP);
        }
        return resultData;
    }

    public static void handleOrderOnException(OrderEntity ret, String shopId) {
        if (!ORDER_QUEUE_CONCURRENT_MAP.get(shopId).contains(ret)) {
            ORDER_QUEUE_CONCURRENT_MAP.get(shopId).add(ret);
        } else {
            ConcurrentLinkedQueue<OrderEntity> concurrentLinkedQueue = new ConcurrentLinkedQueue<OrderEntity>();
            concurrentLinkedQueue.add(ret);
            ORDER_QUEUE_CONCURRENT_MAP.put(shopId, concurrentLinkedQueue);
        }
    }

    public static void handleOrder(OrderEntity ret, String shopId) {
        if (!ORDER_QUEUE_CONCURRENT_MAP.containsKey(shopId)) {
            ORDER_QUEUE_CONCURRENT_MAP.put(shopId, new ConcurrentLinkedQueue<OrderEntity>());
        }
        ORDER_QUEUE_CONCURRENT_MAP.get(shopId).add(ret);
    }
}
