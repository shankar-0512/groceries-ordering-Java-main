package com.groceries.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groceries.app.exception.GroceriesException;
import com.groceries.app.mockedRequest.MockedRequest;
import com.groceries.app.mockedResponse.RepoResponse;
import com.groceries.app.repo.CategoryDetailsRepo;
import com.groceries.app.repo.GroceryDetailsRepo;
import com.groceries.app.repo.OrderGroceryDetailsRepo;
import com.groceries.app.repo.OrderUserDetailsRepo;
import com.groceries.app.repo.UserDetailsRepo;

@SpringBootTest
class GroceriesServiceTest {
	
	@InjectMocks
	@Spy
	GroceriesService groceriesService;
	
	@Mock
	UserDetailsRepo userDetailsRepo;
	
	@Mock
	GroceryDetailsRepo groceryDetailsRepo;
	
	@Mock
	CategoryDetailsRepo categoryDetailsRepo;
	
	@Mock
	OrderUserDetailsRepo orderUserDetailsRepo;
	
	@Mock
	OrderGroceryDetailsRepo orderGroceryDetailsRepo;
	
	private String MapToJson(Object object) throws GroceriesException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	@Test
	void loginAuthSuccessTest() throws GroceriesException, JsonProcessingException {

		Mockito.when(userDetailsRepo.findByUserEmail(Mockito.any())).thenReturn(RepoResponse.fetchUserDetailsSuccess());

		String actualJson = MapToJson(groceriesService.loginAuth(MockedRequest.getLoginRequest()));
		String expectedJson = "{\"responseCode\":0,\"responseMessage\":\"LOGIN SUCCESSFUL\",\"userId\":\"email@gmail.com\",\"userName\":\"Shankar\",\"userAddress\":\"\"}";
		assertThat(actualJson).isEqualTo(expectedJson);
	}
	
	@Test
	void loginAuthInvalidPswTest() throws GroceriesException, JsonProcessingException {

		Mockito.when(userDetailsRepo.findByUserEmail(Mockito.any()))
				.thenReturn(RepoResponse.fetchUserDetailsInvalidPsw());

		String actualJson = MapToJson(groceriesService.loginAuth(MockedRequest.getLoginRequest()));
		String expectedJson = "{\"responseCode\":2,\"responseMessage\":\"INVALID PASSWORD\",\"userId\":null,\"userName\":null,\"userAddress\":null}";
		assertThat(actualJson).isEqualTo(expectedJson);
	}

	@Test
	void loginAuthNullTest() throws GroceriesException, JsonProcessingException {

		Mockito.when(userDetailsRepo.findByUserEmail(Mockito.any())).thenReturn(null);

		String actualJson = MapToJson(groceriesService.loginAuth(MockedRequest.getLoginRequest()));
		String expectedJson = "{\"responseCode\":1,\"responseMessage\":\"USER NOT AVAILABLE\",\"userId\":null,\"userName\":null,\"userAddress\":null}";
		assertThat(actualJson).isEqualTo(expectedJson);
	}

	@SuppressWarnings("serial")
	@Test
	void loginAuthNegTest() throws GroceriesException, JsonProcessingException {

		Mockito.doThrow(new PersistenceException("FAILURE_CASE") {
		}).when(userDetailsRepo).findByUserEmail(Mockito.any());
		Assertions.assertThrows(GroceriesException.class, () -> groceriesService.loginAuth(MockedRequest.getLoginRequest()));
	}

	@Test
	void signUpAuthSuccessTest() throws GroceriesException, JsonProcessingException {

		Mockito.when(userDetailsRepo.findByUserEmail(Mockito.any())).thenReturn(null);

		String actualJson = MapToJson(groceriesService.signUpAuth(MockedRequest.getSignUpRequest()));
		String expectedJson = "{\"responseCode\":3,\"responseMessage\":\"SIGNUP SUCCESSFUL\",\"userId\":null,\"userName\":null,\"userAddress\":null}";
		assertThat(actualJson).isEqualTo(expectedJson);
	}

	@Test
	void signUpAuthUserExistsTest() throws GroceriesException, JsonProcessingException {

		Mockito.when(userDetailsRepo.findByUserEmail(Mockito.any())).thenReturn(RepoResponse.fetchUserDetailsSuccess());

		String actualJson = MapToJson(groceriesService.signUpAuth(MockedRequest.getSignUpRequest()));
		String expectedJson = "{\"responseCode\":4,\"responseMessage\":\"USER ALREADY EXISTS\",\"userId\":null,\"userName\":null,\"userAddress\":null}";
		assertThat(actualJson).isEqualTo(expectedJson);
	}

	@Test
	void signUpAuthPswMisMatchTest() throws GroceriesException, JsonProcessingException {

		Mockito.when(userDetailsRepo.findByUserEmail(Mockito.any())).thenReturn(null);

		String actualJson = MapToJson(groceriesService.signUpAuth(MockedRequest.getSignUpPswMisMatchRequest()));
		String expectedJson = "{\"responseCode\":5,\"responseMessage\":\"PASSWORDS DO NOT MATCH\",\"userId\":null,\"userName\":null,\"userAddress\":null}";
		assertThat(actualJson).isEqualTo(expectedJson);
	}

	@SuppressWarnings("serial")
	@Test
	void signUpAuthNegTest() throws GroceriesException, JsonProcessingException {

		Mockito.doThrow(new PersistenceException("FAILURE_CASE") {
		}).when(userDetailsRepo).findByUserEmail(Mockito.any());

		Assertions.assertThrows(GroceriesException.class, () -> groceriesService.signUpAuth(MockedRequest.getSignUpRequest()));
	}
	
	@Test
	void fetchGroceriesEmptyCategoryTest() throws GroceriesException, JsonProcessingException {
		
		Mockito.when(groceryDetailsRepo.findAll()).thenReturn(RepoResponse.getGroceries());
		
		String actualJson = MapToJson(groceriesService.fetchGroceries(MockedRequest.getGroceriesRequest()));
		String expectedJson = "[{\"id\":1,\"name\":\"Apple\",\"description\":\"NIL\",\"price\":5.0,\"stock\":10}]";
		assertThat(actualJson).isEqualTo(expectedJson);
	}
	
	@Test
	void fetchGroceriesCategoryTest() throws GroceriesException, JsonProcessingException {
		
		Mockito.when(categoryDetailsRepo.findByCategoryName(Mockito.any()))
		.thenReturn(RepoResponse.findCategoryName());
		
		Mockito.when(groceryDetailsRepo.findByCategory(Mockito.any())).thenReturn(RepoResponse.getGroceries());
		
		String actualJson = MapToJson(groceriesService.fetchGroceries(MockedRequest.getGroceriesCategoryRequest()));
		String expectedJson = "[{\"id\":1,\"name\":\"Apple\",\"description\":\"NIL\",\"price\":5.0,\"stock\":10}]";
		assertThat(actualJson).isEqualTo(expectedJson);
	}
	
	@SuppressWarnings("serial")
	@Test
	void fetchGroceriesNegTest() throws GroceriesException, JsonProcessingException {
		
		Mockito.doThrow(new PersistenceException("FAILURE_CASE") {
		}).when(groceryDetailsRepo).findAll();
		
		Assertions.assertThrows(GroceriesException.class, () -> groceriesService.fetchGroceries(MockedRequest.getGroceriesRequest()));
	}
	
	@Test
	void orderGroceriesTest() throws GroceriesException, JsonProcessingException {
		
		Mockito.when(orderUserDetailsRepo.save(Mockito.any())).thenReturn(RepoResponse.getOrderDetails());
		Mockito.when(orderGroceryDetailsRepo.saveAll(Mockito.any())).thenReturn(RepoResponse.getGroceryDetailList());
		
		String actualJson = MapToJson(groceriesService.orderGroceries(MockedRequest.getOrderRequest()));
		String  expectedJson = "{\"responseCode\":null,\"responseMessage\":null}";
		assertThat(actualJson).isEqualTo(expectedJson);
	}
	
	@SuppressWarnings("serial")
	@Test
	void orderGroceriesNegTest() throws GroceriesException, JsonProcessingException {
		
		Mockito.doThrow(new PersistenceException("FAILURE_CASE") {
		}).when(orderUserDetailsRepo).save(Mockito.any());
		
		Assertions.assertThrows(GroceriesException.class, () -> groceriesService.orderGroceries(MockedRequest.getOrderRequest()));
	}
	
	@Test
	void fetchProfileTest() throws GroceriesException, JsonProcessingException {
		
		Mockito.when(userDetailsRepo.findByUserEmail(Mockito.any())).thenReturn(RepoResponse.getUserDetails());
		
		String actualJson = MapToJson(groceriesService.fetchProfile(MockedRequest.getProfileRequest()));
		String expectedJson = "{\"responseCode\":null,\"responseMessage\":null,\"userName\":\"Shankar\",\"userAddress\":\"\"}";
		assertThat(actualJson).isEqualTo(expectedJson);
	}
	
	@SuppressWarnings("serial")
	@Test
	void fetchProfileNegTest() throws GroceriesException, JsonProcessingException {
		
		Mockito.doThrow(new PersistenceException("FAILURE_CASE") {
		}).when(userDetailsRepo).findByUserEmail(Mockito.any());
		
		Assertions.assertThrows(GroceriesException.class, () -> groceriesService.fetchProfile(MockedRequest.getProfileRequest()));
	}

}
