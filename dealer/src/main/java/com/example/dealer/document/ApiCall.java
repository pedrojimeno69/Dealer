package com.example.dealer.document;

import com.example.dealer.enums.ApiCallEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "api_calls")
@Getter
@Setter
public class ApiCall {

    @Id
    private String id;
    private String ip;
    private Date date;
    private ApiCallEnum service;
}
