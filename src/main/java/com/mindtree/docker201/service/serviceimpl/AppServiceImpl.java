package com.mindtree.docker201.service.serviceimpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mindtree.docker201.Util.Logging;
import com.mindtree.docker201.entity.Data;
import com.mindtree.docker201.repository.DataRepo;
import com.mindtree.docker201.service.AppService;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	DataRepo repo;
	
	@Autowired
	Logging logging;

	@Override
	public ResponseEntity<String> addUser(Data user) {
		if (repo.existsById(user.getId())) {
			logging.genLog("error", "User already exist with id " + user.getId());
			return new ResponseEntity<>("Can't able to add, user already exist", HttpStatus.CONFLICT);
		} else {
			repo.save(user);
			logging.log(new Timestamp(new Date().getTime()).toString(), "Added User with id " + user.getId());
			return new ResponseEntity<>("User added successfully", HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Data> getOneUser(int id) {
		if (repo.existsById(id)) {
			Optional<Data> user = repo.findById(id);
			if(user.isPresent()) {
				logging.log(new Timestamp(new Date().getTime()).toString(), "Found user with id " + id);
				return new ResponseEntity<>(user.get(), HttpStatus.OK);
			} else {
				logging.genLog("error", "User doesn't exist with id " + id);
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} else {
			logging.genLog("error", "User doesn't exist with id " + id);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<List<Data>> getAllUsers() {
		logging.log(new Timestamp(new Date().getTime()).toString(), "Get all users");
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteUser(int id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
			logging.log(new Timestamp(new Date().getTime()).toString(), "Deleted user with id " + id);
			return new ResponseEntity<>("User removed successfully", HttpStatus.OK);
		} else {
			logging.genLog("error", "User doesn't exist with id " + id);
			return new ResponseEntity<>("Can't able to delete, user not exist", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> updateUser(Data user) {
		if (repo.existsById(user.getId())) {
			repo.save(user);
			logging.log(new Timestamp(new Date().getTime()).toString(), "Updated user with id " + user.getId());
			return new ResponseEntity<>("User detail updated successfully", HttpStatus.OK);
		} else {
			logging.genLog("error", "User doesn't exist with id " + user.getId());
			return new ResponseEntity<>("Can't able to update, user not exist", HttpStatus.BAD_REQUEST);
		}
	}
}
