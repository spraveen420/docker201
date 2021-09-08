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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindtree.docker201.controller.AppController;
import com.mindtree.docker201.entity.Data;
import com.mindtree.docker201.service.AppService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Docker201ControllerTests {
	
	@Mock
	AppService servTest;
	
	@InjectMocks
	AppController appControllerTest;
	
	@InjectMocks
	Docker201Application docker201Test;
	
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
	   public void main() {
		docker201Test.main(new String[] {});
	   }
	
	
	@Test
	public void addUserTest() {
		when(servTest.addUser(user)).thenReturn(new ResponseEntity<>("Can't able to add, user already exist", HttpStatus.CONFLICT));
		assertEquals("Can't able to add, user already exist", appControllerTest.addUser(user).getBody().toString());
		assertEquals(409, appControllerTest.addUser(user).getStatusCodeValue());
	}
	
	@Test
	public void getOneUserTest() {
		when(servTest.getOneUser(user.getId())).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));
		assertEquals("Sample", appControllerTest.getOneUser(1).getBody().getName());
		assertEquals(200, appControllerTest.getOneUser(1).getStatusCodeValue());
	}
	
	
	@Test
	public void getAllUsersTest() {
		users = setUsersList();
		when(servTest.getAllUsers()).thenReturn(new ResponseEntity<>(users, HttpStatus.OK));
		assertEquals(3, appControllerTest.getAllUsers().getBody().size());
		assertEquals(200, appControllerTest.getAllUsers().getStatusCodeValue());
	}
	
	@Test
	public void deleteUserTest() {
		when(servTest.deleteUser(user.getId())).thenReturn(new ResponseEntity<>("User removed successfully", HttpStatus.OK));
		assertEquals("User removed successfully", appControllerTest.deleteUser(user.getId()).getBody().toString());
		assertEquals(200, appControllerTest.deleteUser(user.getId()).getStatusCodeValue());
	}
	
	@Test
	public void updateUserTest() {
		when(servTest.updateUser(user)).thenReturn(new ResponseEntity<>("Can't able to update, user not exist", HttpStatus.BAD_REQUEST));
		assertEquals("Can't able to update, user not exist", appControllerTest.updateUser(user).getBody().toString());
		assertEquals(400, appControllerTest.updateUser(user).getStatusCodeValue());
	}
	
	@Test
	public void basicTest() {
		assertEquals("Hello World!", appControllerTest.basic().getBody().toString());
		assertEquals(200, appControllerTest.basic().getStatusCodeValue());
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
