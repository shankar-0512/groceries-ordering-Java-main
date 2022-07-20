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
import com.groceries.app.dto.CommonResponseDTO;
import com.groceries.app.dto.GroceriesRequestDTO;
import com.groceries.app.dto.GroceriesResponseDTO;
import com.groceries.app.dto.OrderGroceriesRequestDTO;
import com.groceries.app.dto.OrderedItemsDTO;
import com.groceries.app.entity.GroceryDetailsEntity;
import com.groceries.app.entity.OrderGroceryDetailsEntity;
import com.groceries.app.entity.OrderUserDetailsEntity;
import com.groceries.app.exception.GroceriesException;
import com.groceries.app.repo.CategoryDetailsRepo;
import com.groceries.app.repo.GroceryDetailsRepo;
import com.groceries.app.repo.OrderGroceryDetailsRepo;
import com.groceries.app.repo.OrderUserDetailsRepo;

@Service
public class GroceriesService {

	@Autowired
	GroceryDetailsRepo groceryDetailsRepo;

	@Autowired
	CategoryDetailsRepo categoryDetailsRepo;

	@Autowired
	OrderUserDetailsRepo orderuserDetailsRepo;
	
	@Autowired
	OrderGroceryDetailsRepo orderGroceryDetailsRepo;

	static final Logger logger = LoggerFactory.getLogger(GroceriesService.class);

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
			orderDetails.setStreet(orderGroceriesRequest.getUserDetails().getStreet());
			orderDetails.setCity(orderGroceriesRequest.getUserDetails().getCity());
			orderDetails.setPostalCode(orderGroceriesRequest.getUserDetails().getPostalCode());

			orderDetails = orderuserDetailsRepo.save(orderDetails);
			
			for(OrderedItemsDTO grocery : orderGroceriesRequest.getOrderedItems()) {
				
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

}
