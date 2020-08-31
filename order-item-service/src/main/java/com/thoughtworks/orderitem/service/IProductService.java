package com.thoughtworks.orderitem.service;

import java.util.List;

import com.thoughtworks.orderitem.dto.ProductRequest;
import com.thoughtworks.orderitem.dto.ProductResponse;

public interface IProductService {

	List<ProductResponse> getProducts();
	void createProduct(List<ProductRequest> productRequestList);
}
