package com.mindtree.docker201;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindtree.docker201.Util.Logging;
import com.mindtree.docker201.entity.Data;
import com.mindtree.docker201.repository.DataRepo;
import com.mindtree.docker201.service.serviceimpl.AppServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Docker201ServiceImplTests {
	
	@InjectMocks
	AppServiceImpl servImplTest;

	@Mock
	DataRepo repoTest;
	
	@Mock
	Logging logger;
	
	@Spy
	private static Data user = new Data();
	
	@Spy
	private static List<Data> users = new ArrayList<>();
	
	@Before
	public void setData() {
		user.setId(1);
		user.setName("Sample");
		user.setAge(24);
		System.out.println("User: " + user.getId() + " - " + user.getName() + " - " + user.getAge());
	}

	@Test
	public void addUserTest() {
		when(repoTest.existsById(user.getId())).thenReturn(true);
		doNothing().when(logger).log(Mockito.anyString(), Mockito.anyString());
		assertEquals("Can't able to add, user already exist", servImplTest.addUser(user).getBody().toString());
		assertEquals(409, servImplTest.addUser(user).getStatusCodeValue());
	}
	
	@Test
	public void addUserTest1() {
		when(repoTest.existsById(user.getId())).thenReturn(false);
		when(repoTest.save(user)).thenReturn(user);
		doNothing().when(logger).log(Mockito.anyString(), Mockito.anyString());
		assertEquals("User added successfully", servImplTest.addUser(user).getBody().toString());
		assertEquals(200, servImplTest.addUser(user).getStatusCodeValue());
	}
	
	@Test
	public void getOneUserTest() {
		when(repoTest.existsById(user.getId())).thenReturn(true);
		when(repoTest.findById(user.getId())).thenReturn(Optional.of(user));
		doNothing().when(logger).log(Mockito.anyString(), Mockito.anyString());
		assertEquals("Sample", servImplTest.getOneUser(1).getBody().getName());
		assertEquals(200, servImplTest.getOneUser(1).getStatusCodeValue());
	}
	
	@Test
	public void getOneUserTesta() {
		when(repoTest.existsById(user.getId())).thenReturn(true);
		when(repoTest.findById(user.getId())).thenReturn(Optional.empty());
		doNothing().when(logger).log(Mockito.anyString(), Mockito.anyString());
		assertEquals(null, servImplTest.getOneUser(1).getBody());
		assertEquals(400, servImplTest.getOneUser(1).getStatusCodeValue());
	}
	
	@Test
	public void getOneUserTest1() {
		when(repoTest.existsById(user.getId())).thenReturn(false);
		doNothing().when(logger).log(Mockito.anyString(), Mockito.anyString());
		assertEquals(null, servImplTest.getOneUser(1).getBody());
		assertEquals(400, servImplTest.getOneUser(1).getStatusCodeValue());
	}
	
	@Test
	public void getAllUsersTest() {
		users = setUsersList();
		when(repoTest.findAll()).thenReturn(users);
		doNothing().when(logger).log(Mockito.anyString(), Mockito.anyString());
		assertEquals(3, servImplTest.getAllUsers().getBody().size());
		assertEquals(200, servImplTest.getAllUsers().getStatusCodeValue());
	}
	
	@Test
	public void deleteUserTest() {
		when(repoTest.existsById(user.getId())).thenReturn(true);
		doNothing().when(repoTest).deleteById(Mockito.anyInt());
		doNothing().when(logger).log(Mockito.anyString(), Mockito.anyString());
		assertEquals("User removed successfully", servImplTest.deleteUser(user.getId()).getBody().toString());
		assertEquals(200, servImplTest.deleteUser(user.getId()).getStatusCodeValue());
	}
	
	@Test
	public void deleteUserTest1() {
		when(repoTest.existsById(user.getId())).thenReturn(false);
		doNothing().when(logger).log(Mockito.anyString(), Mockito.anyString());
		assertEquals("Can't able to delete, user not exist", servImplTest.deleteUser(user.getId()).getBody().toString());
		assertEquals(400, servImplTest.deleteUser(user.getId()).getStatusCodeValue());
	}
	
	@Test
	public void updateUserTest() {
		when(repoTest.existsById(user.getId())).thenReturn(false);
		doNothing().when(logger).log(Mockito.anyString(), Mockito.anyString());
		assertEquals("Can't able to update, user not exist", servImplTest.updateUser(user).getBody().toString());
		assertEquals(400, servImplTest.updateUser(user).getStatusCodeValue());
	}
	
	@Test
	public void updateUserTest1() {
		when(repoTest.existsById(user.getId())).thenReturn(true);
		when(repoTest.save(user)).thenReturn(user);
		doNothing().when(logger).log(Mockito.anyString(), Mockito.anyString());
		assertEquals("User detail updated successfully", servImplTest.updateUser(user).getBody().toString());
		assertEquals(200, servImplTest.updateUser(user).getStatusCodeValue());
	}
	
	public List<Data> setUsersList() {
		List<Data> users = new ArrayList<>();
		Data user1 = new Data(1, "Sample", 21);
		Data user2 = new Data(2, "Ample", 22);
		Data user3 = new Data(3, "Fog", 23);
		users.add(user1);
		users.add(user2);
		users.add(user3);
		return users;
	}

}
