package com.example.dealer.service.impl;

import com.example.dealer.dto.CarContainer;
import com.example.dealer.dto.CarFeatures;
import com.example.dealer.entity.CarEntity;
import com.example.dealer.enums.ApiCallEnum;
import com.example.dealer.exception.CarFeatureNotFoundException;
import com.example.dealer.exception.GenerateExcelException;
import com.example.dealer.exception.PropertyNotFoundQueryException;
import com.example.dealer.helper.ExcelHelper;
import com.example.dealer.mapper.CarMapper;
import com.example.dealer.repository.CarRepository;
import com.example.dealer.service.ApiCallService;
import com.example.dealer.service.DealerService;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DealerServiceImpl implements DealerService {

    private static final String LIKE = "like";
    private static final List <String> CONDITIONS_PERMITTED = Collections.unmodifiableList(Arrays.asList("eq", "=", LIKE));
    private static final String DELIMITER = " ";

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ApiCallService apiCallService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Session session;

    @Override
    public CarFeatures retrieveCarByIdentifierOrDate(Integer identifier, Date date) throws CarFeatureNotFoundException {
        apiCallService.log(ApiCallEnum.CAR_BY_IDENTIFICATION_DATE);
        CarEntity carEntity = carRepository.retrieveCarByIdentifierOrDate(identifier, date);

        if (Objects.isNull(carEntity)){
            throw new CarFeatureNotFoundException("Not exist any result by identifier: " + identifier + ", date: " + date);
        }

        return CarMapper.entityToCarFeatures(carEntity, date);
    }

    @Override
    public CarContainer retrieveCarByFilter(String filter) throws PropertyNotFoundQueryException {
        apiCallService.log(ApiCallEnum.CARS_BY_FILTER);

        String[] filterSplit = filter.split(DELIMITER);
        try{

            if (filterSplit.length == 3 && CONDITIONS_PERMITTED.contains(filterSplit[1])){
                List <CarEntity> carEntities = findByFilter(filterSplit);
                if (CollectionUtils.isNotEmpty(carEntities)){
                    return CarMapper.entitiesToCarContainer(carEntities);
                }
            }

        }catch (QueryException e){
            throw new PropertyNotFoundQueryException("Not exist the property " + filterSplit[0] + "into car entity");
        }
        return null;
    }

    private List <CarEntity> findByFilter(String[] filterSplit){
        SimpleExpression expression = LIKE.equals(filterSplit[1]) ?
                Restrictions.like(filterSplit[0], filterSplit[2]) : Restrictions.eq(filterSplit[0], filterSplit[2]);

        return session.createCriteria(CarEntity.class).add(expression).list();
    }

    @Override
    public byte[] generateCarExcel() throws GenerateExcelException {
        Iterable<CarEntity> carEntityIterable = carRepository.findAll();
        List <CarEntity> carEntities = Objects.nonNull(carEntityIterable) ?
                StreamSupport.stream(carEntityIterable.spliterator(), false).collect(Collectors.toList()) : new ArrayList<>();

        return ExcelHelper.generate(carEntities);
    }
}
