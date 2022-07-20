package com.groceries.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="order_grocery_details", schema="public")
public class OrderGroceryDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="serial")
	private Integer serial;
	
	@Column(name="order_id")
	private Integer orderId;
	
	@Column(name="grocery_id")
	private Integer groceryId;
	
	@Column(name="grocery_name")
	private String groceryName;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="quantity")
	private Integer quantity;

}
