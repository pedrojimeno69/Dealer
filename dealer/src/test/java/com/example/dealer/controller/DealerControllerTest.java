package com.example.dealer.controller;

import com.example.dealer.controller.impl.DealerControllerImpl;
import com.example.dealer.dto.*;
import com.example.dealer.exception.CarFeatureNotFoundException;
import com.example.dealer.exception.GenerateExcelException;
import com.example.dealer.exception.PropertyNotFoundQueryException;
import com.example.dealer.service.DealerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.util.NestedServletException;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DealerControllerTest {

    private static final Date DATE = new Date();
    private static final String RETRIEVE_CAR_URL = "/api/dealer/coches/specifications?carId=1&initDate=2021-05-18T00:00:00Z";
    private static final String RETRIEVE_CAR_BY_FILTER_URL = "/api/dealer/coches/?filter=";
    private static final String DOWNLOAD_EXCEL_URL = "/api/dealer/coches/excel";

    @Mock
    private DealerService dealerService;

    @InjectMocks
    private DealerControllerImpl controller;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void retrieveCarByIdentifierOrDateTest_OK() throws Exception {

        when(dealerService.retrieveCarByIdentifierOrDate(eq(1), any(Date.class))).thenReturn(carFeaturesMock());

        mockMvc.perform(
                get(RETRIEVE_CAR_URL)
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(dealerService, times(1)).retrieveCarByIdentifierOrDate(eq(1), any(Date.class));

    }

    @Test (expected = NestedServletException.class)
    public void retrieveCarByIdentifierOrDateTest_ThrowCarFeatureNotFoundException() throws Exception {

        when(dealerService.retrieveCarByIdentifierOrDate(eq(1), any(Date.class))).thenThrow(CarFeatureNotFoundException.class);

        mockMvc.perform(
                get(RETRIEVE_CAR_URL)
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError());

        verify(dealerService, times(1)).retrieveCarByIdentifierOrDate(eq(1), any(Date.class));
    }

    @Test
    public void retrieveCarByFilterTest_OK() throws Exception {

        String filter = "nameModel eq Focus";
        when(dealerService.retrieveCarByFilter(filter)).thenReturn(carContainerMock());

        mockMvc.perform(
                get(RETRIEVE_CAR_BY_FILTER_URL + filter)
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(dealerService, times(1)).retrieveCarByFilter(filter);

    }

    @Test (expected = NestedServletException.class)
    public void retrieveCarByFilter_ThrowCarFeatureNotFoundException() throws Exception {

        String filter = "propertyNotExist eq Focus";
        when(dealerService.retrieveCarByFilter(filter)).thenThrow(PropertyNotFoundQueryException.class);

        mockMvc.perform(
                get(RETRIEVE_CAR_BY_FILTER_URL + filter)
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError());

        verify(dealerService, times(1)).retrieveCarByFilter(filter);
    }

    @Test
    public void downloadExcel_OK() throws Exception {

        when(dealerService.generateCarExcel()).thenReturn(new byte[1]);

        mockMvc.perform(
                get(DOWNLOAD_EXCEL_URL)
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        verify(dealerService, times(1)).generateCarExcel();

    }

    @Test (expected = NestedServletException.class)
    public void downloadExcel_ThrowGenerateExcelException() throws Exception {

        when(dealerService.generateCarExcel()).thenThrow(GenerateExcelException.class);

        mockMvc.perform(
                get(DOWNLOAD_EXCEL_URL))
                .andExpect(status().isInternalServerError());

        verify(dealerService, times(1)).generateCarExcel();
    }

    private CarFeatures carFeaturesMock(){
        CarFeatures carFeatures = new CarFeatures();
        carFeatures.setCarId(1);
        carFeatures.setMarkId(1);

        carFeatures.setPrices(Arrays.asList(priceMock()));
        return carFeatures;
    }

    private CarContainer carContainerMock(){
        CarContainer carContainer = new CarContainer();
        Car car = new Car();
        car.setId(1);
        car.setColor("red");
        car.setModelName("Focus");

        Mark mark = new Mark();
        mark.setId(1);
        mark.setName("Ford");

        car.setMark(mark);
        car.setPrices(Arrays.asList(priceMock()));
        carContainer.getCars().add(car);

        return carContainer;
    }

    private Price priceMock(){
        Price price = new Price();
        price.setId(1);
        price.setInitDate(DATE);
        price.setEndDate(DATE);
        return price;
    }
}
