package com.example.dealer.service;

import com.example.dealer.dto.CarContainer;
import com.example.dealer.dto.CarFeatures;
import com.example.dealer.entity.CarEntity;
import com.example.dealer.entity.MarkEntity;
import com.example.dealer.entity.PriceEntity;
import com.example.dealer.enums.ApiCallEnum;
import com.example.dealer.exception.CarFeatureNotFoundException;
import com.example.dealer.exception.GenerateExcelException;
import com.example.dealer.exception.PropertyNotFoundQueryException;
import com.example.dealer.repository.CarRepository;
import com.example.dealer.service.impl.DealerServiceImpl;
import org.hibernate.Criteria;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DealerServiceTest {

    @Mock
    private CarRepository repository;

    @Mock
    private Session session;

    @Mock
    private Criteria criteria;

    @Mock
    private ApiCallService apiCallService;

    @InjectMocks
    private DealerServiceImpl service;

    @Test
    public void retrieveCarByIdentifierOrDateTest_OK() throws CarFeatureNotFoundException {
        doNothing().when(apiCallService).log(eq(ApiCallEnum.CAR_BY_IDENTIFICATION_DATE));
        when(repository.retrieveCarByIdentifierOrDate(eq(1), any(Date.class))).thenReturn(carEntityMock());

        CarFeatures result = service.retrieveCarByIdentifierOrDate(1, new Date());

        Assert.assertNotNull(result);
        Assert.assertEquals(Integer.valueOf(1), result.getCarId());
        Assert.assertEquals(Integer.valueOf(2), result.getMarkId());
        Assert.assertNotNull(result.getPrices());
        Assert.assertEquals(Integer.valueOf(3), result.getPrices().get(0).getId());

        verify(apiCallService, times(1)).log(eq(ApiCallEnum.CAR_BY_IDENTIFICATION_DATE));
        verify(repository, times(1)).retrieveCarByIdentifierOrDate(eq(1), any(Date.class));
    }

    @Test (expected = CarFeatureNotFoundException.class)
    public void retrieveCarByIdentifierOrDateTest_throwCarFeatureNotFoundException() throws CarFeatureNotFoundException {
        doNothing().when(apiCallService).log(eq(ApiCallEnum.CAR_BY_IDENTIFICATION_DATE));
        when(repository.retrieveCarByIdentifierOrDate(eq(1), any(Date.class))).thenReturn(null);

        CarFeatures result = service.retrieveCarByIdentifierOrDate(1, new Date());

        Assert.assertNull(result);
        verify(apiCallService, times(1)).log(eq(ApiCallEnum.CAR_BY_IDENTIFICATION_DATE));
        verify(repository, times(1)).retrieveCarByIdentifierOrDate(eq(1), any(Date.class));
    }

    @Test
    public void retrieveCarByFilterTest_OK() throws PropertyNotFoundQueryException {
        String filter = "modelName like Mondeo";
        doNothing().when(apiCallService).log(eq(ApiCallEnum.CARS_BY_FILTER));
        when(session.createCriteria(eq(CarEntity.class))).thenReturn(criteria);
        when(criteria.add(any())).thenReturn(criteria);
        when(criteria.list()).thenReturn(Arrays.asList(carEntityMock()));

        CarContainer result = service.retrieveCarByFilter(filter);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.getCars().size() > 0);
        Assert.assertEquals(Integer.valueOf(1), result.getCars().get(0).getId());
        Assert.assertEquals("Focus", result.getCars().get(0).getModelName());
        Assert.assertEquals("red", result.getCars().get(0).getColor());
        Assert.assertNotNull(result.getCars().get(0).getMark());
        Assert.assertNotNull(result.getCars().get(0).getPrices());

        verify(apiCallService, times(1)).log(eq(ApiCallEnum.CARS_BY_FILTER));
        verify(session, times(1)).createCriteria(eq(CarEntity.class));
        verify(criteria, times(1)).add(any());
        verify(criteria, times(1)).list();
    }

    @Test
    public void retrieveCarByFilterTest_NoContainDelimiter() throws PropertyNotFoundQueryException {
        String filter = "modelName Mondeo";
        doNothing().when(apiCallService).log(eq(ApiCallEnum.CARS_BY_FILTER));

        CarContainer result = service.retrieveCarByFilter(filter);

        Assert.assertNull(result);
        verify(apiCallService, times(1)).log(eq(ApiCallEnum.CARS_BY_FILTER));
    }

    @Test (expected = PropertyNotFoundQueryException.class)
    public void retrieveCarByFilterTest_throwPropertyNotFoundQueryException() throws PropertyNotFoundQueryException {
        String filter = "propertyNotFound like Mondeo";
        doNothing().when(apiCallService).log(eq(ApiCallEnum.CARS_BY_FILTER));
        when(session.createCriteria(eq(CarEntity.class))).thenReturn(criteria);
        when(criteria.add(any())).thenThrow(QueryException.class);

        service.retrieveCarByFilter(filter);

        verify(apiCallService, times(1)).log(eq(ApiCallEnum.CARS_BY_FILTER));
        verify(session, times(1)).createCriteria(eq(CarEntity.class));
        verify(criteria, times(1)).add(any());
    }

    @Test
    public void generateCarExcel_OK() throws GenerateExcelException {
        when(repository.findAll()).thenReturn(Arrays.asList(carEntityMock()));

        byte [] result = service.generateCarExcel();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.length > 0);

        verify(repository, times(1)).findAll();
    }

    private CarEntity carEntityMock(){
        CarEntity carEntity = new CarEntity();
        carEntity.setId(1);
        carEntity.setColor("red");
        carEntity.setModelName("Focus");

        MarkEntity markEntity = new MarkEntity();
        markEntity.setId(2);
        markEntity.setName("Ford");
        markEntity.setCars(Arrays.asList(carEntity));

        PriceEntity priceEntity = priceEntityMock(18, 19, 15000.00);
        priceEntity.setCar(carEntity);

        carEntity.setMark(markEntity);
        carEntity.setPriceEntities(Arrays.asList(priceEntity));

        return carEntity;
    }

    private PriceEntity priceEntityMock(int dayOfInitDate, int dayOfEndDate, double price){
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId(3);
        priceEntity.setInitDate(new GregorianCalendar(2021, Calendar.MAY, dayOfInitDate).getTime());
        priceEntity.setEndDate(new GregorianCalendar(2021, Calendar.MAY, dayOfEndDate).getTime());
        priceEntity.setPrice(new Double(price));
        return priceEntity;
    }
}
