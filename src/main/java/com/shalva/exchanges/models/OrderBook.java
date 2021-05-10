package com.shalva.exchanges.models;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderBook {

    TreeMap<Double, Integer> bids = new TreeMap<>();
    TreeMap<Double, Integer> asks = new TreeMap<>();
}
