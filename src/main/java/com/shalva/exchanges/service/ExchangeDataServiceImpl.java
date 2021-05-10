package com.shalva.exchanges.service;

import com.shalva.exchanges.models.Exchange;
import com.shalva.exchanges.models.OrderBook;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

@Service
public class ExchangeDataServiceImpl implements ExchangeDataService, OrderBookDataService {

    private static final OrderBook orderBook = new OrderBook();

    @Override
    public void fetchData(Exchange exchange) {
        orderBook.getBids().merge(exchange.getBid(), 1, Integer::sum);
        orderBook.getAsks().merge(exchange.getAsk(), 1, Integer::sum);

        System.out.println("Best Bid " + orderBook.getBids().lastEntry());
        System.out.println("Best ask " + orderBook.getAsks().firstEntry());
        System.out.println("Whole Map " + orderBook);
        System.out.println(new Date());
    }

    @Override
    public void getData(Exchange exchange) {
        getDataViaWebSocket(exchange);
    }


    private void getDataViaWebSocket(Exchange exchange) {
        final boolean[] f = {false};
        try {
            WebSocketClient webSocketClient = new StandardWebSocketClient();
            WebSocketSession webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) {
                    if (!ObjectUtils.isEmpty(message) && !ObjectUtils.isEmpty(message.getPayload())) {
                        String payload = message.getPayload();
                        if (exchange.validate(payload)) {
                            System.out.println("received message - " + message.getPayload() + " " + new Date());
                            exchange.pharseIncomingData(payload);
                            fetchData(exchange);
                        }
                    }
                    try {
                        if (!f[0]) {
                            WebSocketMessage<String> webSocketMessage = new TextMessage(exchange.getRequest());
                            session.sendMessage(webSocketMessage);
                            f[0] = true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterConnectionEstablished(WebSocketSession session) {
                    System.out.println("established connection - " + session);
                }
            }, new WebSocketHttpHeaders(), URI.create(exchange.getUrl())).get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
