package com.groceries.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.groceries.app.entity.CategoryDetailsEntity;

@Repository
public interface CategoryDetailsRepo extends JpaRepository<CategoryDetailsEntity, Integer> {
	
	@Query(value = "select category_id from groceries.category_details where category_name=:categoryName", nativeQuery=true)
	Integer findByCategoryName(@Param("categoryName") String category);

}
