package com.thoughtworks.order.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.thoughtworks.order.dto.ProductResponse;

@FeignClient(value = "product", url = "http://localhost:4000")
public interface IProductClient {

	@GetMapping("products")
	List<ProductResponse> getProducts();
}
