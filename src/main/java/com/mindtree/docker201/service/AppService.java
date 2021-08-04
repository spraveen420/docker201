package com.mindtree.docker201.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mindtree.docker201.entity.Data;

public interface AppService {

	public ResponseEntity<String> addUser(Data user);

	public ResponseEntity<Data> getOneUser(int id);

	public ResponseEntity<List<Data>> getAllUsers();

	public ResponseEntity<String> deleteUser(int id);

	public ResponseEntity<String> updateUser(Data user);
}
