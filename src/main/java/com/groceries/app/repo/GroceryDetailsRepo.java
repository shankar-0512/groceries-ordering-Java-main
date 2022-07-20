package com.groceries.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.groceries.app.entity.GroceryDetailsEntity;

@Repository
public interface GroceryDetailsRepo extends JpaRepository<GroceryDetailsEntity, Integer> {
	
	@Query(value = "select * from public.grocery_details where category_id=:categoryId", nativeQuery = true)
	List<GroceryDetailsEntity> findByCategory(@Param("categoryId") Integer categoryId);

}
