package com.shalva.exchanges.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract class Exchange {

    Double ask;
    Double bid;
    String url;
    String request;

    public Exchange(String url, String request) {
        this.url = url;
        this.request = request;
    }

    public abstract void pharseIncomingData(String message);

    public abstract boolean validate(String message);
}
