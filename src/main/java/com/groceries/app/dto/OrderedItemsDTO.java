package com.groceries.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedItemsDTO {
	
	Integer amount;
	Integer id;
	String name;
	Double price;

}
