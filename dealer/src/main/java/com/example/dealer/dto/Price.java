package com.example.dealer.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Price {

    private Integer id;
    private Date initDate;
    private Date endDate;
    private Double price;
}
