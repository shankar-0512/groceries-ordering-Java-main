package com.groceries.app.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groceries.app.controller.GroceriesController;
import com.groceries.app.dto.CommonResponseDTO;
import com.groceries.app.dto.FetchProfileRequestDTO;
import com.groceries.app.dto.FetchProfileResponseDTO;
import com.groceries.app.dto.GroceriesResponseDTO;
import com.groceries.app.dto.LoginResponseDTO;
import com.groceries.app.exception.GroceriesException;
import com.groceries.app.mockedRequest.MockedRequest;
import com.groceries.app.service.GroceriesService;

@SpringBootTest
class GroceriesControllerTest {
	
	@InjectMocks
	@Spy
	GroceriesController groceriesController;

	@Mock
	GroceriesService groceriesService;
	
	@Test
	void loginAuthTest() throws GroceriesException, JsonProcessingException {

		Mockito.when(groceriesService.loginAuth(Mockito.any())).thenReturn(new LoginResponseDTO());
		LoginResponseDTO response = groceriesController.loginAuth(MockedRequest.getLoginRequest());

		assertThat(response.getResponseCode()).isNull();
	}
	
	@Test
	void signUpAuthTest() throws GroceriesException, JsonProcessingException {

		Mockito.when(groceriesService.signUpAuth(Mockito.any())).thenReturn(new LoginResponseDTO());
		LoginResponseDTO response = groceriesController.loginAuth(MockedRequest.getSignUpRequest());

		assertThat(response.getResponseCode()).isNull();
	}

	@Test
	void loginAuthNegTest() throws GroceriesException, JsonProcessingException {

		Mockito.doThrow(new GroceriesException("FAILURE_CASE")).when(groceriesService).loginAuth(Mockito.any());
		LoginResponseDTO response = groceriesController.loginAuth(MockedRequest.getLoginRequest());

		assertThat(response.getResponseCode()).isNull();
	}
	
	@Test
	void fetchGroceriesTest() throws GroceriesException, JsonProcessingException {

		List<GroceriesResponseDTO> groceriesResponse = new ArrayList<>();
		Mockito.when(groceriesService.fetchGroceries(Mockito.any())).thenReturn(groceriesResponse);
		groceriesResponse = groceriesController.fetchGroceries(MockedRequest.getGroceriesRequest());

		assertThat(groceriesResponse).isEmpty();
	}

	@Test
	void fetchGroceriesNegTest() throws GroceriesException, JsonProcessingException {

		List<GroceriesResponseDTO> groceriesResponse = new ArrayList<>();
		Mockito.doThrow(new GroceriesException("FAILURE_CASE")).when(groceriesService).fetchGroceries(Mockito.any());
		groceriesResponse = groceriesController.fetchGroceries(MockedRequest.getGroceriesRequest());

		assertThat(groceriesResponse).isEmpty();
	}
	
	@Test
	void orderGroceriesTest() throws GroceriesException, JsonProcessingException {

		Mockito.when(groceriesService.orderGroceries(Mockito.any())).thenReturn(new CommonResponseDTO());
		CommonResponseDTO response = groceriesController.orderGroceries(MockedRequest.getOrderRequest());

		assertThat(response.getResponseCode()).isZero();
	}
	
	@Test
	void orderGroceriesNegTest() throws GroceriesException, JsonProcessingException {

		Mockito.doThrow(new GroceriesException("FAILURE_CASE")).when(groceriesService).orderGroceries(Mockito.any());
		CommonResponseDTO response = groceriesController.orderGroceries(MockedRequest.getOrderRequest());

		assertThat(response.getResponseCode()).isEqualByComparingTo(1);
	}
	
	@Test
	void fetchProfileTest() throws GroceriesException, JsonProcessingException {

		Mockito.when(groceriesService.fetchProfile(Mockito.any())).thenReturn(new FetchProfileResponseDTO());
		FetchProfileResponseDTO response = groceriesController.fetchProfile(MockedRequest.getProfileRequest());

		assertThat(response.getResponseCode()).isZero();
	}
	
	@Test
	void fetchProfileNegTest() throws GroceriesException, JsonProcessingException {

		Mockito.doThrow(new GroceriesException("FAILURE_CASE")).when(groceriesService).fetchProfile(Mockito.any());
		FetchProfileResponseDTO response = groceriesController.fetchProfile(MockedRequest.getProfileRequest());

		assertThat(response.getResponseCode()).isEqualByComparingTo(1);
	}

}
