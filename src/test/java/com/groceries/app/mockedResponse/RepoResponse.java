package com.groceries.app.mockedResponse;

import java.util.ArrayList;
import java.util.List;

import com.groceries.app.entity.GroceryDetailsEntity;
import com.groceries.app.entity.OrderGroceryDetailsEntity;
import com.groceries.app.entity.OrderUserDetailsEntity;
import com.groceries.app.entity.UserDetailsEntity;

public class RepoResponse {
	
	public static UserDetailsEntity fetchUserDetailsSuccess() {
		UserDetailsEntity entity = new UserDetailsEntity();
		entity.setUserEmail("email@gmail.com");
		entity.setPassword("123456");
		entity.setUserId(1);
		entity.setUserName("Shankar");
		entity.setAddress("");

		return entity;
	}
	
	public static UserDetailsEntity fetchUserDetailsInvalidPsw() {
		UserDetailsEntity entity = new UserDetailsEntity();
		entity.setUserEmail("email@gmail.com");
		entity.setPassword("54321");
		entity.setUserId(1);
		entity.setUserName("Shankar");
		entity.setAddress("");

		return entity;
	}

	public static List<GroceryDetailsEntity> getGroceries() {
		List<GroceryDetailsEntity> entityList = new ArrayList<>();
		GroceryDetailsEntity entity = new GroceryDetailsEntity();
		
		entity.setCategoryId(1);
		entity.setGroceryDescription("NIL");
		entity.setGroceryId(1);
		entity.setGroceryName("Apple");
		entity.setGroceryPrice(5.0);
		entity.setStockAvailable(10);
		entityList.add(entity);
		
		return entityList;
	}

	public static Integer findCategoryName() {
		return 1;
	}

	public static OrderUserDetailsEntity getOrderDetails() {
		OrderUserDetailsEntity entity = new OrderUserDetailsEntity();
		entity.setOrderId(1);
		entity.setAddress("");
		entity.setUserName("Shankar");
		
		return entity;
	}

	public static List<OrderGroceryDetailsEntity> getGroceryDetailList() {
		List<OrderGroceryDetailsEntity> entityList = new ArrayList<>();
		
		return entityList;
	}

	public static UserDetailsEntity getUserDetails() {
		UserDetailsEntity entity = new UserDetailsEntity();
		entity.setUserEmail("");
		entity.setPassword("Shanky@0512");
		entity.setAddress("");
		entity.setUserId(1);
		entity.setUserName("Shankar");
		
		return entity;
	}
}
