package com.shalva.exchanges.models;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderBook {

    Map<Double, Integer> bids = new HashMap<>();
    Map<Double, Integer> asks = new HashMap<>();
}
