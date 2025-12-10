package com.springboot.phasezero_catalog_service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.springboot.phasezero_catalog_service.entity.Product;
import com.springboot.phasezero_catalog_service.repository.ProductRepository;
@Repository
public class ProductDao {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}
	
	public Boolean existsByPartNumber(String partNumber) {
		return productRepository.existsByPartNumber(partNumber);
	}
	
	public Boolean existsByPartName(String partName) {
		return productRepository.existsByPartName(partName);
	}
	
	public List<Product> listAllProducts(){
		return productRepository.findAll();
	}
	
	public List<Product> searchByName(String partName){
		return productRepository.findByPartNameContainingIgnoreCase(partName);
	}
	
	public List<Product> filterByCategory(String Category){
		return productRepository.findByCategoryIgnoreCase(Category);
	}
	
	public List<Product> sortProductsByPrice(String field){
		return productRepository.findAll(Sort.by(field).ascending());
	}
	
	public List<Product> totalInventoryValue() {
		return productRepository.findAll();
	}
	
}
