package com.spring.mongo.app.dto;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private ObjectId id;

	private String userName;

	private String email;

	private boolean sentimentAnalysis;

	private String password;

	private List<String> roles;
}
