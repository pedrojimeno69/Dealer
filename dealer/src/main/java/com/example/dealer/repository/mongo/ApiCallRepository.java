package com.example.dealer.repository.mongo;

import com.example.dealer.document.ApiCall;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiCallRepository extends MongoRepository<ApiCall, String> {
}
