package com.groceries.app.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.groceries.app.constants.AppConstants;
import com.groceries.app.constants.ErrorConstants;
import com.groceries.app.constants.SuccessConstants;
import com.groceries.app.dto.CommonResponseDTO;
import com.groceries.app.dto.FetchProfileRequestDTO;
import com.groceries.app.dto.FetchProfileResponseDTO;
import com.groceries.app.dto.GroceriesRequestDTO;
import com.groceries.app.dto.GroceriesResponseDTO;
import com.groceries.app.dto.LoginDTO;
import com.groceries.app.dto.LoginResponseDTO;
import com.groceries.app.dto.OrderGroceriesRequestDTO;
import com.groceries.app.dto.OrderedItemsDTO;
import com.groceries.app.entity.GroceryDetailsEntity;
import com.groceries.app.entity.OrderGroceryDetailsEntity;
import com.groceries.app.entity.OrderUserDetailsEntity;
import com.groceries.app.entity.UserDetailsEntity;
import com.groceries.app.exception.GroceriesException;
import com.groceries.app.repo.CategoryDetailsRepo;
import com.groceries.app.repo.GroceryDetailsRepo;
import com.groceries.app.repo.OrderGroceryDetailsRepo;
import com.groceries.app.repo.OrderUserDetailsRepo;
import com.groceries.app.repo.UserDetailsRepo;

@Service
public class GroceriesService {

	@Autowired
	UserDetailsRepo userDetailsRepo;

	@Autowired
	GroceryDetailsRepo groceryDetailsRepo;

	@Autowired
	CategoryDetailsRepo categoryDetailsRepo;

	@Autowired
	OrderUserDetailsRepo orderuserDetailsRepo;

	@Autowired
	OrderGroceryDetailsRepo orderGroceryDetailsRepo;

	static final Logger logger = LoggerFactory.getLogger(GroceriesService.class);

	public LoginResponseDTO loginAuth(LoginDTO loginRequest) throws GroceriesException {

		LoginResponseDTO loginResponse = new LoginResponseDTO();
		UserDetailsEntity userEntity = new UserDetailsEntity();
		try {

			userEntity = userDetailsRepo.findByUserEmail(loginRequest.getEmail().toLowerCase());
			if (userEntity == null) {
				loginResponse.setResponseCode(AppConstants.FAILURE_CODE_1);
				loginResponse.setResponseMessage(ErrorConstants.USER_NOT_AVAILABLE);
				return loginResponse;
			} else if (!userEntity.getPassword().equals(loginRequest.getPassword())) {
				loginResponse.setResponseCode(AppConstants.FAILURE_CODE_2);
				loginResponse.setResponseMessage(ErrorConstants.INVALID_PASSWORD);
				return loginResponse;
			} else {
				loginResponse.setResponseCode(AppConstants.SUCCESS_CODE_0);
				loginResponse.setResponseMessage(SuccessConstants.LOGIN_SUCCESSFUL);
				loginResponse.setUserId(userEntity.getUserEmail());
				loginResponse.setUserName(userEntity.getUserName());
				loginResponse.setUserAddress(userEntity.getAddress());
				return loginResponse;
			}

		} catch (DataAccessException | PersistenceException ex) {
			logger.error(ErrorConstants.LOGIN_AUTH_ERROR);
			throw new GroceriesException(ErrorConstants.LOGIN_AUTH_ERROR);
		}
	}

	public LoginResponseDTO signUpAuth(LoginDTO signUpRequest) throws GroceriesException {

		LoginResponseDTO signUpResponse = new LoginResponseDTO();
		UserDetailsEntity userEntity = new UserDetailsEntity();
		UserDetailsEntity userExistsCheck = new UserDetailsEntity();
		try {

			userExistsCheck = userDetailsRepo.findByUserEmail(signUpRequest.getEmail().toLowerCase());

			// Validations
			if (userExistsCheck != null) {
				signUpResponse.setResponseCode(AppConstants.FAILURE_CODE_4);
				signUpResponse.setResponseMessage(ErrorConstants.USER_ALREADY_EXISTS);
				return signUpResponse;
			}

			if (!signUpRequest.getPassword().equals(signUpRequest.getRePassword())) {
				signUpResponse.setResponseCode(AppConstants.FAILURE_CODE_5);
				signUpResponse.setResponseMessage(ErrorConstants.PASSWORDS_DO_NOT_MATCH);
				return signUpResponse;
			}
			// End of Validations

			userEntity.setUserName(signUpRequest.getName());
			userEntity.setUserEmail(signUpRequest.getEmail().toLowerCase());
			userEntity.setPassword(signUpRequest.getPassword());
			userEntity.setAddress(signUpRequest.getAddress());

			userDetailsRepo.save(userEntity);
			signUpResponse.setResponseCode(AppConstants.FAILURE_CODE_3);
			signUpResponse.setResponseMessage(SuccessConstants.SIGNUP_SUCCESSFUL);

		} catch (DataAccessException | PersistenceException ex) {
			logger.error(ErrorConstants.SIGNUP_ERROR);
			throw new GroceriesException(ErrorConstants.SIGNUP_ERROR);
		}
		return signUpResponse;
	}

	public List<GroceriesResponseDTO> fetchGroceries(GroceriesRequestDTO groceriesRequest) throws GroceriesException {

		List<GroceryDetailsEntity> groceryEntityList = new ArrayList<>();
		List<GroceriesResponseDTO> groceriesResponseList = new ArrayList<>();

		try {

			if (groceriesRequest.getCategory().equals(AppConstants.EMPTY_STR)) {
				groceryEntityList = groceryDetailsRepo.findAll();
			} else {
				Integer categoryId = categoryDetailsRepo.findByCategoryName(groceriesRequest.getCategory());
				groceryEntityList = groceryDetailsRepo.findByCategory(categoryId);
			}

			for (GroceryDetailsEntity entity : groceryEntityList) {

				GroceriesResponseDTO groceriesResponse = new GroceriesResponseDTO();
				groceriesResponse.setId(entity.getGroceryId());
				groceriesResponse.setName(entity.getGroceryName());
				groceriesResponse.setDescription(entity.getGroceryDescription());
				groceriesResponse.setPrice(entity.getGroceryPrice());
				groceriesResponse.setStock(entity.getStockAvailable());

				groceriesResponseList.add(groceriesResponse);
			}

		} catch (DataAccessException | PersistenceException e) {
			logger.error(ErrorConstants.FETCH_GROCERIES_ERROR);
			throw new GroceriesException(ErrorConstants.FETCH_GROCERIES_ERROR);
		}

		return groceriesResponseList;
	}

	public CommonResponseDTO orderGroceries(OrderGroceriesRequestDTO orderGroceriesRequest) throws GroceriesException {

		CommonResponseDTO orderResponse = new CommonResponseDTO();
		OrderUserDetailsEntity orderDetails = new OrderUserDetailsEntity();
		List<OrderGroceryDetailsEntity> groceryDetailList = new ArrayList<>();
		try {

			orderDetails.setUserName(orderGroceriesRequest.getUserDetails().getName());
			orderDetails.setAddress(orderGroceriesRequest.getUserDetails().getAddress());

			orderDetails = orderuserDetailsRepo.save(orderDetails);

			for (OrderedItemsDTO grocery : orderGroceriesRequest.getOrderedItems()) {

				OrderGroceryDetailsEntity groceryDetail = new OrderGroceryDetailsEntity();
				groceryDetail.setOrderId(orderDetails.getOrderId());
				groceryDetail.setGroceryId(grocery.getId());
				groceryDetail.setGroceryName(grocery.getName());
				groceryDetail.setPrice(grocery.getPrice());
				groceryDetail.setQuantity(grocery.getAmount());

				groceryDetailList.add(groceryDetail);
			}

			orderGroceryDetailsRepo.saveAll(groceryDetailList);

		} catch (DataAccessException | PersistenceException e) {
			logger.error(ErrorConstants.ORDER_GROCERIES_ERROR);
			throw new GroceriesException(ErrorConstants.ORDER_GROCERIES_ERROR);

		}

		return orderResponse;
	}

	public FetchProfileResponseDTO fetchProfile(FetchProfileRequestDTO fetchProfileRequest) throws GroceriesException {
		FetchProfileResponseDTO profileResponse = new FetchProfileResponseDTO();
		UserDetailsEntity userDetail = new UserDetailsEntity();
		try {

			userDetail = userDetailsRepo.findByUserEmail(fetchProfileRequest.getUserEmail());
			profileResponse.setUserName(userDetail.getUserName());
			profileResponse.setUserAddress(userDetail.getAddress());

		} catch (DataAccessException | PersistenceException e) {
			logger.error(ErrorConstants.FETCH_PROFILE_ERROR);
			throw new GroceriesException(ErrorConstants.FETCH_PROFILE_ERROR);
		}
		return profileResponse;
	}

}
