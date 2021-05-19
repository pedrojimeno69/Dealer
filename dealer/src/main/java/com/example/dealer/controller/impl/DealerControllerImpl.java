package com.example.dealer.controller.impl;

import com.example.dealer.controller.DealerController;
import com.example.dealer.dto.CarContainer;
import com.example.dealer.dto.CarFeatures;
import com.example.dealer.exception.GenerateExcelException;
import com.example.dealer.exception.PropertyNotFoundQueryException;
import com.example.dealer.service.DealerService;
import com.example.dealer.exception.CarFeatureNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DealerControllerImpl implements DealerController {

    @Autowired
    private DealerService dealerService;

    @Override
    public ResponseEntity<CarFeatures> retrieveCarByIdentifierOrDate(@RequestParam(name = "initDate") Date date,
                                                                     @RequestParam(name = "carId") Integer carId) throws CarFeatureNotFoundException {
        return new ResponseEntity<>(dealerService.retrieveCarByIdentifierOrDate(carId, date), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarContainer> retrieveCarByFilter(@RequestParam(name = "filter") String filter) throws PropertyNotFoundQueryException {
        return new ResponseEntity<>(dealerService.retrieveCarByFilter(filter), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadExcel() throws GenerateExcelException {

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=car_list.xlsx");

        byte [] bytes = dealerService.generateCarExcel();

        return new ResponseEntity<>(new ByteArrayResource(bytes), header, HttpStatus.CREATED);
    }
}
