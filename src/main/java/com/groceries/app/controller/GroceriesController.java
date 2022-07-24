package com.groceries.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.groceries.app.constants.URIConstants;
import com.groceries.app.dto.CommonResponseDTO;
import com.groceries.app.dto.FetchProfileRequestDTO;
import com.groceries.app.dto.FetchProfileResponseDTO;
import com.groceries.app.dto.GroceriesRequestDTO;
import com.groceries.app.dto.GroceriesResponseDTO;
import com.groceries.app.dto.LoginDTO;
import com.groceries.app.dto.LoginResponseDTO;
import com.groceries.app.dto.OrderGroceriesRequestDTO;
import com.groceries.app.constants.AppConstants;
import com.groceries.app.constants.ErrorConstants;
import com.groceries.app.constants.SuccessConstants;
import com.groceries.app.exception.GroceriesException;
import com.groceries.app.service.GroceriesService;

@RestController
public class GroceriesController {

	public static final Logger logger = LoggerFactory.getLogger(GroceriesController.class);

	@Autowired
	GroceriesService groceriesService;
	
	@PostMapping(URIConstants.LOGIN_AUTH)
	public LoginResponseDTO loginAuth(@RequestBody LoginDTO loginRequest) {

		LoginResponseDTO loginResponse = new LoginResponseDTO();
		try {
			if (loginRequest.getSignUpF().equals(AppConstants.N_STR)) {
				loginResponse = groceriesService.loginAuth(loginRequest);
			} else if (loginRequest.getSignUpF().equals(AppConstants.Y_STR)) {
				loginResponse = groceriesService.signUpAuth(loginRequest);
			}
		} catch (GroceriesException ex) {
			logger.error(ErrorConstants.LOGIN_AUTH_ERROR);
		}

		return loginResponse;
	}

	@PostMapping(URIConstants.FETCH_GROCERIES)
	public List<GroceriesResponseDTO> fetchGroceries(@RequestBody GroceriesRequestDTO groceriesRequest) {

		List<GroceriesResponseDTO> groceriesResponse = new ArrayList<>();
		try {
			groceriesResponse = groceriesService.fetchGroceries(groceriesRequest);
		} catch (GroceriesException ex) {
			logger.error(ErrorConstants.FETCH_GROCERIES_ERROR);
		}
		return groceriesResponse;
	}
	
	@PostMapping(URIConstants.ORDER_GROCERIES)
	public CommonResponseDTO orderGroceries(@RequestBody OrderGroceriesRequestDTO orderGroceriesRequest) {

		CommonResponseDTO orderGroceriesResponse = new CommonResponseDTO();
		try {
			orderGroceriesResponse = groceriesService.orderGroceries(orderGroceriesRequest);
			orderGroceriesResponse.setResponseCode(AppConstants.SUCCESS_CODE_0);
			orderGroceriesResponse.setResponseMessage(SuccessConstants.ORDER_GROCERIES_SUCCESS);
		} catch (GroceriesException ex) {
			logger.error(ErrorConstants.ORDER_GROCERIES_ERROR);
			orderGroceriesResponse.setResponseCode(AppConstants.FAILURE_CODE_1);
			orderGroceriesResponse.setResponseMessage(ErrorConstants.ORDER_GROCERIES_ERROR);
		}
		return orderGroceriesResponse;
	}
	
	@PostMapping(URIConstants.FETCH_PROFILE)
	public FetchProfileResponseDTO fetchProfile(@RequestBody FetchProfileRequestDTO fetchProfileRequest) {

		FetchProfileResponseDTO fetchProfileResponse = new FetchProfileResponseDTO();
		try {
			fetchProfileResponse = groceriesService.fetchProfile(fetchProfileRequest);
			fetchProfileResponse.setResponseCode(AppConstants.SUCCESS_CODE_0);
			fetchProfileResponse.setResponseMessage(SuccessConstants.FETCH_PROFILE_SUCCESS);
		} catch (GroceriesException ex) {
			logger.error(ErrorConstants.FETCH_PROFILE_ERROR);
			fetchProfileResponse.setResponseCode(AppConstants.FAILURE_CODE_1);
			fetchProfileResponse.setResponseMessage(ErrorConstants.FETCH_PROFILE_ERROR);
		}
		return fetchProfileResponse;
	}

}
