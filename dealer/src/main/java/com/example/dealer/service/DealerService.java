package com.example.dealer.service;

import com.example.dealer.dto.CarContainer;
import com.example.dealer.dto.CarFeatures;
import com.example.dealer.exception.CarFeatureNotFoundException;
import com.example.dealer.exception.GenerateExcelException;
import com.example.dealer.exception.PropertyNotFoundQueryException;

import java.util.Date;

public interface DealerService {

    CarFeatures retrieveCarByIdentifierOrDate(Integer identifier, Date date) throws CarFeatureNotFoundException;

    byte[] generateCarExcel() throws GenerateExcelException;

    CarContainer retrieveCarByFilter(String filter) throws PropertyNotFoundQueryException;
}
