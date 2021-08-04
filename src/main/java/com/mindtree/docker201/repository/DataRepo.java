package com.mindtree.docker201.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindtree.docker201.entity.Data;

public interface DataRepo extends MongoRepository<Data, Integer> {

}
