package com.spring.mongo.app.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ShutDownController {

	private final ApplicationContext applicationContext;

	@PostMapping(value = "/shutdown")
	@ResponseStatus(HttpStatus.OK)
	public void shutdown() {
		SpringApplication.exit(applicationContext);
	}
}
