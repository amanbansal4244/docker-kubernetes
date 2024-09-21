package com.spring.mongo.app.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.mongo.app.entity.ProductEntity;

public interface ProductRepository extends MongoRepository<ProductEntity, ObjectId> {

}