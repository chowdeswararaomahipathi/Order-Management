package com.thoughtworks.orderitem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.orderitem.dto.ProductRequest;
import com.thoughtworks.orderitem.dto.ProductResponse;
import com.thoughtworks.orderitem.entity.Product;
import com.thoughtworks.orderitem.repository.IProductRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProductService implements IProductService {

	@Autowired
	private IProductRepository iProductRepository;

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<ProductResponse> getProducts() {

		log.info("before calling iProductRepository for getting the products");
		List<Product> productList = iProductRepository.findAll();
		log.info("After calling iProductRepository for getting the products");

		List<ProductResponse> productResponseList = productList.parallelStream()
				.map(ProductService::convertToProductResponse).collect(Collectors.toList());

		return productResponseList;
	}

	@Override
	public void createProduct(List<ProductRequest> productRequestList) {

		List<Product> productList = productRequestList.parallelStream().map(ProductService::convertToProduct)
				.collect(Collectors.toList());

		iProductRepository.saveAll(productList);

	}

	private static ProductResponse convertToProductResponse(Product product) {
		return objectMapper.convertValue(product, ProductResponse.class);
	}

	private static Product convertToProduct(ProductRequest productRequest) {
		return objectMapper.convertValue(productRequest, Product.class);
	}

}
