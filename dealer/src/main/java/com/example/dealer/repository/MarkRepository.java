package com.example.dealer.repository;

import com.example.dealer.entity.MarkEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends CrudRepository <MarkEntity, Long> {
}
