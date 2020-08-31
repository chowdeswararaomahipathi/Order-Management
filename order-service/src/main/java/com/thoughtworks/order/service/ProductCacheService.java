package com.thoughtworks.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.thoughtworks.order.client.IProductClient;
import com.thoughtworks.order.dto.ProductResponse;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Service
public class ProductCacheService {

	@Autowired
	private IProductClient iProductClient;

	@Cacheable(value = "products", cacheManager = "OrderServiceCacheManager")
	public List<ProductResponse> getProducts() {
		log.info("before calling the iProductClient for getting products");
		return iProductClient.getProducts();
	}
	
	
}
