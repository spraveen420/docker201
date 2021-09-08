package com.mindtree.docker201.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mindtree.docker201.entity.Data;
import com.mindtree.docker201.repository.DataRepo;
import com.mindtree.docker201.service.AppService;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	DataRepo repo;

	@Override
	public ResponseEntity<String> addUser(Data user) {
		if (repo.existsById(user.getId())) {
			return new ResponseEntity<>("Can't able to add, user already exist", HttpStatus.CONFLICT);
		} else {
			repo.save(user);
			return new ResponseEntity<>("User added successfully", HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Data> getOneUser(int id) {
		if (repo.existsById(id)) {
			Optional<Data> user = repo.findById(id);
			if(user.isPresent()) {
				return new ResponseEntity<>(user.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<List<Data>> getAllUsers() {
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteUser(int id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
			return new ResponseEntity<>("User removed successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Can't able to delete, user not exist", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> updateUser(Data user) {
		if (repo.existsById(user.getId())) {
			repo.save(user);
			return new ResponseEntity<>("User detail updated successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Can't able to update, user not exist", HttpStatus.BAD_REQUEST);
		}
	}
}
