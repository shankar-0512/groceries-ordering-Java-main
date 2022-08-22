package com.groceries.app.mockedRequest;

import java.util.ArrayList;
import java.util.List;

import com.groceries.app.dto.FetchProfileRequestDTO;
import com.groceries.app.dto.GroceriesRequestDTO;
import com.groceries.app.dto.LoginDTO;
import com.groceries.app.dto.OrderGroceriesRequestDTO;
import com.groceries.app.dto.OrderedItemsDTO;
import com.groceries.app.dto.UserDetailsDTO;

public class MockedRequest {
	
	public static LoginDTO getLoginRequest() {
		LoginDTO login = new LoginDTO();
		login.setEmail("email@gmail.com");
		login.setPassword("123456");
		login.setSignUpF("N");

		return login;
	}
	
	public static LoginDTO getSignUpRequest() {
		LoginDTO login = new LoginDTO();
		login.setEmail("email@gmail.com");
		login.setPassword("12345");
		login.setRePassword("12345");
		login.setAddress("Anakaputhur");
		login.setSignUpF("Y");

		return login;
	}
	
	public static GroceriesRequestDTO getGroceriesRequest() {
		GroceriesRequestDTO request = new GroceriesRequestDTO();
		request.setCategory("");
		return request;
	}
	
	public static GroceriesRequestDTO getGroceriesCategoryRequest() {
		GroceriesRequestDTO request = new GroceriesRequestDTO();
		request.setCategory("fruits");
		return request;
	}
	
	public static OrderGroceriesRequestDTO getOrderRequest() {
		OrderGroceriesRequestDTO request = new OrderGroceriesRequestDTO();
		UserDetailsDTO user = new UserDetailsDTO();
		user.setAddress("");
		user.setName("Shankar");
		List<OrderedItemsDTO> orderList = new ArrayList<>();
		OrderedItemsDTO order = new OrderedItemsDTO();
		order.setAmount(1);
		order.setId(1);
		order.setName("Coco Cola");
		order.setPrice(25.00);
		orderList.add(order);
		
		request.setOrderedItems(orderList);
		request.setUserDetails(user);
		return request;
	}
	
	public static FetchProfileRequestDTO getProfileRequest() {
		FetchProfileRequestDTO request = new FetchProfileRequestDTO();
		request.setUserEmail("");
		return request;
	}
	
	public static LoginDTO getSignUpPswMisMatchRequest() {
		LoginDTO login = new LoginDTO();
		login.setEmail("email@gmail.com");
		login.setPassword("12345");
		login.setRePassword("123456");
		login.setAddress("");
		login.setSignUpF("Y");

		return login;
	}

}
