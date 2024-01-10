package com.bilgeadam.teknikservis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bilgeadam.teknikservis.model.Product;
import com.bilgeadam.teknikservis.repository.ProductRepository;
@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	public List<Product> GetAllProduct(){
		return productRepository.getAll();
		
	}
}
