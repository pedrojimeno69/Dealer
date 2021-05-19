package com.example.dealer.mapper;

import com.example.dealer.dto.*;
import com.example.dealer.entity.CarEntity;
import com.example.dealer.entity.PriceEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CarMapper {

    public static CarFeatures entityToCarFeatures(CarEntity carEntity, Date dateToCompare){
        CarFeatures carFeatures = new CarFeatures();

        carFeatures.setCarId(carEntity.getId());
        carFeatures.setMarkId(Objects.nonNull(carEntity.getMark()) ? carEntity.getMark().getId() : null);

        List <PriceEntity> priceEntitiesFiltered = CollectionUtils.isNotEmpty(carEntity.getPriceEntities())
                ? carEntity.getPriceEntities().stream().filter(p -> dateToCompare.compareTo(p.getInitDate()) >= 0
                && dateToCompare.compareTo(p.getEndDate()) <=0 ).collect(Collectors.toList()) : new ArrayList<>();

        for (PriceEntity priceEntity : priceEntitiesFiltered){
            Price price = new Price();
            BeanUtils.copyProperties(priceEntity, price);
            carFeatures.getPrices().add(price);
        }
        return carFeatures;
    }

    public static CarContainer entitiesToCarContainer(List<CarEntity> entities){
        CarContainer carContainer = new CarContainer();
        if (CollectionUtils.isNotEmpty(entities)){
            entities.stream().forEach(entity -> {
                Car car = entityToDto(entity);
                carContainer.getCars().add(car);
            });
        }
        return carContainer;
    }

    public static Car entityToDto(CarEntity carEntity){
        Car car = new Car();
        BeanUtils.copyProperties(carEntity, car);

        for (PriceEntity priceEntity : carEntity.getPriceEntities()){
            Price price = new Price();
            BeanUtils.copyProperties(priceEntity, price);
            car.getPrices().add(price);
        }

        if (Objects.nonNull(carEntity.getMark())){
            Mark mark = new Mark();
            BeanUtils.copyProperties(carEntity.getMark(), mark);
            car.setMark(mark);
        }

        return car;
    }
}
