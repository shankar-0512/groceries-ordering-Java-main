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
@Table(name="grocery_details", schema="groceries")
public class GroceryDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer groceryId;
	
	@Column(name="name")
	private String groceryName;
	
	@Column(name="description")
	private String groceryDescription;
	
	@Column(name="price")
	private Double groceryPrice;
	
	@Column(name="stock_available")
	private Integer stockAvailable;
}
