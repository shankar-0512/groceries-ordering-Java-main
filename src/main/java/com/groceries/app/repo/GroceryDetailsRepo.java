package com.groceries.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groceries.app.entity.GroceryDetailsEntity;

@Repository
public interface GroceryDetailsRepo extends JpaRepository<GroceryDetailsEntity, Integer> {

}
