package com.spring.mongo.app.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.mongo.app.entity.ProductEntity;
import com.spring.mongo.app.repository.ProductRepository;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/product")
	public String saveProduct(@RequestBody ProductEntity product) {
		productRepository.save(product);
		return "Product added successfully::" + product.getId();

	}

	@GetMapping("/products")
	public List<ProductEntity> getProducts() {
		return productRepository.findAll();
	}

	@GetMapping("/product/{id}")
	public Optional<ProductEntity> getProduct(@PathVariable ObjectId id) {
		return productRepository.findById(id);
	}

	@DeleteMapping("/product/{id}")
	public String deleteProduct(@PathVariable ObjectId id) {
		productRepository.deleteById(id);
		return "Deleted Product Successfully::" + id;
	}

}