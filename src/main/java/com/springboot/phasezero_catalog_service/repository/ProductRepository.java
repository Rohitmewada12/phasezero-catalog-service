package com.springboot.phasezero_catalog_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.phasezero_catalog_service.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	Boolean existsByPartNumber(String PartNumber);
	
	Boolean existsByPartName(String PartName);
	
	
	List<Product> findByPartNameContainingIgnoreCase(String PartName);
	
	
	List<Product> findByCategoryIgnoreCase(String Category);
	
}

