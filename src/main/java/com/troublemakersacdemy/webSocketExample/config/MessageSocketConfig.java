package com.troublemakersacdemy.webSocketExample.config;

import com.troublemakersacdemy.webSocketExample.domain.MessageRequest;
import com.troublemakersacdemy.webSocketExample.domain.MessageResponse;
import com.troublemakersacdemy.webSocketExample.service.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;


import java.util.Map;

@Configuration
public class MessageSocketConfig {

    @Bean
    SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler webSocketHandler) {
        return new SimpleUrlHandlerMapping(Map.of("/websocket/message", webSocketHandler), 10);
    }

    @Bean
    WebSocketHandler webSocketHandler(MessageService messageService) {
        return session -> {
            var webSocketMessageFlux = session.receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .map(MessageRequest::new)
                    .flatMap(messageService::handleMessage)
                    .map(MessageResponse::getReply)
                    .map(session::textMessage);
            return session.send(webSocketMessageFlux);
        };
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
