package com.groceries.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroceriesResponseDTO {
	
	Integer id;
	String name;
	String description;
	Double price;
	Integer stock;

}
