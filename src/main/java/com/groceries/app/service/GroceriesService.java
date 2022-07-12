package com.groceries.app.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.groceries.app.constants.ErrorConstants;
import com.groceries.app.dto.GroceriesResponseDTO;
import com.groceries.app.entity.GroceryDetailsEntity;
import com.groceries.app.exception.GroceriesException;
import com.groceries.app.repo.GroceryDetailsRepo;

@Service
public class GroceriesService {

	@Autowired
	GroceryDetailsRepo groceryDetailsRepo;

	static final Logger logger = LoggerFactory.getLogger(GroceriesService.class);

	public List<GroceriesResponseDTO> fetchGroceries() throws GroceriesException {

		List<GroceryDetailsEntity> groceryEntityList = new ArrayList<>();
		List<GroceriesResponseDTO> groceriesResponseList = new ArrayList<>();

		try {

			groceryEntityList = groceryDetailsRepo.findAll();

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

}
