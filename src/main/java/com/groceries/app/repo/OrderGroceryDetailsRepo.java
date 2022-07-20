package com.groceries.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groceries.app.entity.OrderGroceryDetailsEntity;

@Repository
public interface OrderGroceryDetailsRepo extends JpaRepository<OrderGroceryDetailsEntity, Integer> {

}
