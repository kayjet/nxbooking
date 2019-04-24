package com.booking.background.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * WSSessionManager
 *
 * @author kai.liu
 * @date 2018/06/06
 */
public class WSSessionManager {
    private static final Logger logger = LoggerFactory.getLogger(WSSessionManager.class);

    private static final ConcurrentMap<String, WebSocketSession> WEB_SOCKET_SESSION_CONCURRENT_MAP = new ConcurrentHashMap<String, WebSocketSession>();
    private static final ConcurrentMap<String, String> WEB_SOCKET_SESSION_SHOP_ID_REL_MAP = new ConcurrentHashMap<String, String>();


    public static WebSocketSession getSession(String shopId) {
        logger.info("getSession shopId = " + shopId);
        if (WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.containsKey(shopId)) {
            String wsSessionId = WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.get(shopId);
            return WEB_SOCKET_SESSION_CONCURRENT_MAP.get(wsSessionId);
        }
        return null;
    }

    public static void removeSessionRel(String wsSessionId) {
        Iterator<String> shopIdIterator = WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.keySet().iterator();
        while (shopIdIterator.hasNext()) {
            String shopId = shopIdIterator.next();
            if (WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.get(shopId).equals(wsSessionId)) {
                shopIdIterator.remove();
            }
        }
    }

    public static void addSession(WebSocketSession webSocketSession) {
        WEB_SOCKET_SESSION_CONCURRENT_MAP.put(webSocketSession.getId(), webSocketSession);
    }

    public static void removeSession(String sessionId) {
        WEB_SOCKET_SESSION_CONCURRENT_MAP.remove(sessionId);
    }

    public static void addSessionId(String shopId, String sessionId) {
        if (!WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.containsKey(shopId)) {
            WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.put(shopId, sessionId);
        }
        logger.info("WEB_SOCKET_SESSION_SHOP_ID_REL_MAP = " + WEB_SOCKET_SESSION_SHOP_ID_REL_MAP);
    }

}
