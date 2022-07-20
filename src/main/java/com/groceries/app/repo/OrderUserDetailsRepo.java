package com.groceries.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groceries.app.entity.OrderUserDetailsEntity;

@Repository
public interface OrderUserDetailsRepo extends JpaRepository<OrderUserDetailsEntity, Integer> {

}
