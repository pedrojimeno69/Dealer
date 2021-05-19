package com.example.dealer.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Car {

    private Integer id;
    private String modelName;
    private String color;
    private Mark mark;
    private List<Price> prices = new ArrayList<>();
}
