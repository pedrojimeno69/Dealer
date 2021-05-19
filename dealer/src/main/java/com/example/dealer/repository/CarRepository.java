package com.example.dealer.repository;

import com.example.dealer.entity.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CarRepository extends CrudRepository <CarEntity, Long> {


    @Query("SELECT C FROM CarEntity C JOIN C.mark M JOIN C.priceEntities P WHERE C.id = :id AND (P.initDate <= :date AND P.endDate >= :date)")
    CarEntity retrieveCarByIdentifierOrDate(@Param("id") Integer id, @Param("date") Date date);
}
