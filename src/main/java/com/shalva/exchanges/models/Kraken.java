package com.shalva.exchanges.models;

import org.json.JSONArray;
import org.json.JSONObject;

public class Kraken extends Exchange {

    public Kraken() {
    }

    public Kraken(String url, String request) {
        super(url, request);
    }

    @Override
    public void pharseIncomingData(String message) {
        try {
            JSONArray jsonarray = new JSONArray(message);
            JSONObject jsonObject = jsonarray.getJSONObject(1);
            String a = jsonObject.getString("a");
            String b = jsonObject.getString("b");
            String[] aSplit = a.split(",");
            String[] bSplit = b.split(",");
            this.ask = Double.parseDouble(aSplit[0].substring(2, aSplit[0].length() - 1));
            this.bid = Double.parseDouble(bSplit[0].substring(2, bSplit[0].length() - 1));
            System.out.println("data from kraken");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validate(String message) {
        return !message.contains("event");
    }
}
