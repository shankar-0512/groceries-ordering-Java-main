package com.groceries.app.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderGroceriesRequestDTO {
	
	List<OrderedItemsDTO> orderedItems;
	UserDetailsDTO userDetails;

}
