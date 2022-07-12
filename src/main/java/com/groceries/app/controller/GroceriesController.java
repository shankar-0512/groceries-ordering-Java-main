package com.groceries.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groceries.app.constants.URIConstants;
import com.groceries.app.dto.GroceriesResponseDTO;
import com.groceries.app.constants.ErrorConstants;
import com.groceries.app.exception.GroceriesException;
import com.groceries.app.service.GroceriesService;

@RestController
public class GroceriesController { 

	public static final Logger logger = LoggerFactory.getLogger(GroceriesController.class);

	@Autowired
	GroceriesService groceriesService;

	@PostMapping(URIConstants.FETCH_GROCERIES)
	public List<GroceriesResponseDTO> fetchGroceries() {

		List<GroceriesResponseDTO> groceriesResponse = new ArrayList<>();
		try {
			groceriesResponse = groceriesService.fetchGroceries();
		} catch (GroceriesException ex) {
			logger.error(ErrorConstants.FETCH_GROCERIES_ERROR);
		}
		return groceriesResponse;
	}

}
