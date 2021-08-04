package com.mindtree.docker201.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.docker201.entity.Data;
import com.mindtree.docker201.service.AppService;

@RestController
@CrossOrigin
public class AppController {

	@Autowired
	AppService serv;
	
	@GetMapping("/")
	public ResponseEntity<String> basic() {
		
		return new ResponseEntity<>("Hello World!", HttpStatus.OK);
	}

	@PostMapping("/addUser")
	public ResponseEntity<String> addUser(@RequestBody Data user) {
		return serv.addUser(user);
	}

	@GetMapping("/getOneUser/{id}")
	public ResponseEntity<Data> getOneUser(@PathVariable int id) {
		return serv.getOneUser(id);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<Data>> getAllUsers() {
		return serv.getAllUsers();
	}

	@PutMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody Data user) {
		return serv.updateUser(user);
	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id) {
		return serv.deleteUser(id);
	}
}
