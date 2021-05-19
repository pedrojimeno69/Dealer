package com.example.dealer.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarContainer {

    private List<Car> cars = new ArrayList<>();
}
