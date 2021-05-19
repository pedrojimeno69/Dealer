package com.example.dealer.controller;

import com.example.dealer.dto.CarContainer;
import com.example.dealer.dto.CarFeatures;
import com.example.dealer.exception.CarFeatureNotFoundException;
import com.example.dealer.exception.GenerateExcelException;
import com.example.dealer.exception.PropertyNotFoundQueryException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@RequestMapping("/api/dealer")
public interface DealerController {

    @RequestMapping(method = RequestMethod.GET, value = "/coches/specifications", produces = "application/json")
     ResponseEntity<CarFeatures> retrieveCarByIdentifierOrDate(@RequestParam(name = "initDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date date,
                                                               @RequestParam(name = "carId") Integer carId) throws CarFeatureNotFoundException;

    @RequestMapping(method = RequestMethod.GET, value = "/coches", produces = "application/json")
    ResponseEntity<CarContainer> retrieveCarByFilter(@RequestParam(name = "filter") String filter) throws PropertyNotFoundQueryException;

    @RequestMapping(method = RequestMethod.GET, value = "/coches/excel")
    ResponseEntity<ByteArrayResource> downloadExcel() throws GenerateExcelException;
}
