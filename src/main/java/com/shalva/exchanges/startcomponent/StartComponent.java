package com.shalva.exchanges.startcomponent;

import com.shalva.exchanges.models.Bitfinex;
import com.shalva.exchanges.models.Exchange;
import com.shalva.exchanges.models.Kraken;
import com.shalva.exchanges.service.ExchangeDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class StartComponent implements CommandLineRunner {

    private final String bitfinexUrl = "wss://api-pub.bitfinex.com/ws/2";
    private final String krakenUrl = "wss://ws.kraken.com";
    private final String bitfinexRequest = "{ \"event\": \"subscribe\", \"channel\": \"ticker\", \"symbol\": \"tBTCUSD\"}";
    private final String krakenRequest = "{\n" +
            "  \"event\": \"subscribe\",\n" +
            "  \"pair\": [\n" +
            "    \"BTC/USD\"\n" +
            "  ],\n" +
            "  \"subscription\": {\n" +
            "    \"name\": \"ticker\"\n" +
            "  }\n" +
            "}";

    private final ExchangeDataService exchangeDataService;

    public StartComponent(ExchangeDataService exchangeDataService) {
        this.exchangeDataService = exchangeDataService;
    }

    @Override
    public void run(String... args) throws Exception {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Exchange kraken = new Kraken(krakenUrl, krakenRequest);
                exchangeDataService.getData(kraken);
            }
        }, 1000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Exchange bitfinex = new Bitfinex(bitfinexUrl, bitfinexRequest);
                exchangeDataService.getData(bitfinex);
            }
        }, 1000);
//




    }
}
