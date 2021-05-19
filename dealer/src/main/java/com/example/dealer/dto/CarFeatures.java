package com.example.dealer.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarFeatures {

    private Integer carId;
    private Integer markId;
    private List<Price> prices = new ArrayList<>();
}
