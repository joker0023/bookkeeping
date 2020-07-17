package com.jokerstation.bookkeeping.service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint("/ws/{sid}")
@Component
public class WsServerEndpoint {

	private static Logger logger = LoggerFactory.getLogger(WsServerEndpoint.class);

	private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

	public void sendMessage(String sid, String message) throws IOException {
		Session session = sessionPools.get(sid);
		if (session != null) {
			session.getBasicRemote().sendText(message);
		}
	}

	@OnOpen
	public void onOpen(Session session, @PathParam(value = "sid") String sid) throws Exception {
		if (sessionPools.contains(sid)) {
			session.close();
		} else {
			sessionPools.put(sid, session);
		}
	}

	@OnClose
	public void onClose(@PathParam(value = "sid") String sid) {
		sessionPools.remove(sid);
	}

	@OnMessage
	public void onMessage(String message) throws IOException {

	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		logger.error("webSocket error", throwable);
	}
}
