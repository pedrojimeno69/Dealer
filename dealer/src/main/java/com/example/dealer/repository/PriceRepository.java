package com.example.dealer.repository;

import com.example.dealer.entity.PriceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends CrudRepository <PriceEntity, Long> {
}
