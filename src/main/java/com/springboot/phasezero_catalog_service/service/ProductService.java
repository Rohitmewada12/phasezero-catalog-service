package com.springboot.phasezero_catalog_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.phasezero_catalog_service.dao.ProductDao;
import com.springboot.phasezero_catalog_service.dto.ResponseStructure;
import com.springboot.phasezero_catalog_service.entity.Product;
import com.springboot.phasezero_catalog_service.exception.DataNotFoundException;
import com.springboot.phasezero_catalog_service.exception.NegativeValueException;
import com.springboot.phasezero_catalog_service.exception.ObjectIsNotValid;
import com.springboot.phasezero_catalog_service.exception.PartNameNotFound;
import com.springboot.phasezero_catalog_service.exception.PartNumberAlreadyExists;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;

	public ResponseEntity<ResponseStructure<Product>> addProducts(Product product) {

		ResponseStructure<Product> responseStructure = new ResponseStructure<Product>();
		if (product.getPartNumber() != null && product.getPartName() != null && product.getCategory() != null
				&& product.getPrice() != null && product.getStock() != null) {
			String partNumber = product.getPartNumber().trim();
			String partName = product.getPartName().trim().toLowerCase();
			String category = product.getCategory().trim();
			if (!productDao.existsByPartNumber(partNumber)) {
				if (!(product.getPrice() < 0) && !(product.getStock() < 0)) {
					responseStructure.setMessage("Product details saved");
					product.setPartName(partName);
					product.setCategory(category);
					product.setPartNumber(partNumber);
					responseStructure.setData(productDao.addProduct(product));

					responseStructure.setStatusCode(HttpStatus.CREATED.value());
					return new ResponseEntity<ResponseStructure<Product>>(responseStructure, HttpStatus.CREATED);
				} else {
					throw new NegativeValueException("The field is negative");
				}
			} else {
				throw new PartNumberAlreadyExists(product.getPartNumber() + " this number is already exists");
			}
		} else {
			throw new ObjectIsNotValid("Must have to pass all the fields");
		}

	}
	
	
	
	public ResponseEntity<ResponseStructure<List<Product>>> listAllProducts(){
		
		ResponseStructure<List<Product>> responseStructure = new ResponseStructure<List<Product>>();
		responseStructure.setMessage("List of All products");
		responseStructure.setData(productDao.listAllProducts());
		responseStructure.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<List<Product>>>(responseStructure,HttpStatus.OK);
	}
	
	
	public ResponseEntity<ResponseStructure<List<Product>>> searchByName(String partName){
		List<Product> list = productDao.searchByName(partName);
		if(!list.isEmpty()) {
			ResponseStructure<List<Product>> responseStructure = new ResponseStructure<List<Product>>();
			responseStructure.setMessage("Fetching Data based on PartName");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setData(productDao.searchByName(partName));
			return new ResponseEntity<ResponseStructure<List<Product>>>(responseStructure,HttpStatus.OK);
		}else {
			throw new PartNameNotFound(partName+" is not there in the database");
		}
		
	}
	
	
	public ResponseEntity<ResponseStructure<List<Product>>> filterByCategory(String Category){
		List<Product> list = productDao.filterByCategory(Category);
		if(!list.isEmpty()) {
			ResponseStructure<List<Product>> responseStructure = new ResponseStructure<List<Product>>();
			responseStructure.setMessage("Filter all the category");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setData(list);
			return new ResponseEntity<ResponseStructure<List<Product>>>(responseStructure,HttpStatus.OK);
		}else {
			throw new DataNotFoundException(Category+" is not there in the database");
		}
	}
	
	
	public ResponseEntity<ResponseStructure<List<Product>>> sortProductsByPrice(String field){
		List<Product> list = productDao.sortProductsByPrice(field);
		ResponseStructure<List<Product>> responseStructure = new ResponseStructure<List<Product>>();
		responseStructure.setMessage("Filter all the category");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setData(list);
		return new ResponseEntity<ResponseStructure<List<Product>>>(responseStructure,HttpStatus.OK);
	}
	
	
	public ResponseEntity<ResponseStructure<Double>> totalInventoryValue() {
		ResponseStructure<Double> responseStructure = new ResponseStructure<Double>();
		responseStructure.setMessage("Total inventory");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setData(productDao.totalInventoryValue().stream().mapToDouble(p -> p.getPrice() * p.getStock()).sum());
		return new ResponseEntity<ResponseStructure<Double>>(responseStructure, HttpStatus.OK);
	}

}
