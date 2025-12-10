package com.springboot.phasezero_catalog_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.phasezero_catalog_service.dto.ResponseStructure;
import com.springboot.phasezero_catalog_service.entity.Product;
import com.springboot.phasezero_catalog_service.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Product>> addProduct(@RequestBody Product product){
		return productService.addProducts(product);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Product>>> listAllProduct(){
		return productService.listAllProducts();
	}
	
	@GetMapping("/search/{partName}")
	public ResponseEntity<ResponseStructure<List<Product>>> searchByName(@PathVariable String partName){
		return productService.searchByName(partName);
	}
	
	@GetMapping("/filter/{category}")
	public ResponseEntity<ResponseStructure<List<Product>>> searchByCategory(@PathVariable String category){
		return productService.filterByCategory(category);
	}
	
	@GetMapping("/sort")
	public ResponseEntity<ResponseStructure<List<Product>>> sortByPrice(){
		return productService.sortProductsByPrice("price");
	}
	
	@GetMapping("/inventory/value")
	public ResponseEntity<ResponseStructure<Double>> inventoryValue() {
		return productService.totalInventoryValue();
	}
}
