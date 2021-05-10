package com.shalva.exchanges.models;

import org.json.JSONArray;

public class Bitfinex extends Exchange {

    public Bitfinex() {
    }

    public Bitfinex(String url, String request) {
        super(url, request);
    }

    @Override
    public void pharseIncomingData(String message) {
        try {
            JSONArray jsonarray = new JSONArray(message);
            String jsonobject = jsonarray.getString(1);
            String[] split = jsonobject.split(",");
            this.bid = Double.parseDouble(split[0].substring(1));
            this.ask = Double.parseDouble(split[2]);
            System.out.println("data from Bitfinex");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validate(String message) {
        return !message.contains("event") && !message.contains("hb");
    }
}
