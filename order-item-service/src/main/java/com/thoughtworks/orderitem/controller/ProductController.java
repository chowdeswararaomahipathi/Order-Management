package com.thoughtworks.orderitem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtworks.orderitem.dto.ErrorObject;
import com.thoughtworks.orderitem.dto.ProductRequest;
import com.thoughtworks.orderitem.dto.ProductResponse;
import com.thoughtworks.orderitem.service.IProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("products")
@Log4j2
public class ProductController {

	@Autowired
	private IProductService iProductService;

	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Create products")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Products Created"),
			@ApiResponse(code = 400, message = "Invalid products") })
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody List<ProductRequest> productRequestList) {

		try {
			if (productRequestList == null || productRequestList.isEmpty()) {
				return new ResponseEntity<>(new ErrorObject("Input data can't be null or empty"), HttpStatus.BAD_REQUEST);
			}
			if (Optional.ofNullable(productRequestList).isPresent()) {

				boolean anyEmptyProductName = productRequestList.parallelStream().anyMatch(
						itemRequest -> itemRequest.getProductName() == null || itemRequest.getProductName().isEmpty());
				if (anyEmptyProductName) {
					return new ResponseEntity<>(new ErrorObject("ProductName Can't be empty or null"),
							HttpStatus.BAD_REQUEST);
				}
				boolean anyEmptyProductCode = productRequestList.parallelStream()
						.anyMatch(itemRequest -> itemRequest.getProductCode() == null
								|| itemRequest.getProductCode() == 0 || itemRequest.getProductCode() < 0);
				if (anyEmptyProductCode) {
					return new ResponseEntity<>(new ErrorObject("productCode can't be zero or negative"),
							HttpStatus.BAD_REQUEST);
				}

				boolean anyEmptyPrice = productRequestList.parallelStream()
						.anyMatch(itemRequest -> itemRequest.getPrice() == null || itemRequest.getPrice() == 0
								|| itemRequest.getPrice() < 0);
				if (anyEmptyPrice) {
					return new ResponseEntity<>(new ErrorObject("Price can't be zero or negative"), HttpStatus.BAD_REQUEST);
				}

				boolean anyEmptyQuantity = productRequestList.parallelStream()
						.anyMatch(itemRequest -> itemRequest.getAvailableQuantity() == null
								|| itemRequest.getAvailableQuantity() == 0 || itemRequest.getAvailableQuantity() < 0);
				if (anyEmptyQuantity) {
					return new ResponseEntity<>(new ErrorObject("Price can't be zero or negative"), HttpStatus.BAD_REQUEST);
				}

			}

			log.info("Calling iProductService for creating the Product");
			iProductService.createProduct(productRequestList);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiOperation(value = "Get all products")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retreieved all products", response = ProductResponse.class, responseContainer = "List"),
			@ApiResponse(code = 204, message = "No Content") })
	@GetMapping
	public ResponseEntity<?> getProducts() {

		log.info("Before Calling iProductService for getting all Products");
		List<ProductResponse> productResponseList = iProductService.getProducts();
		log.info("After Calling iProductService for getting all Products");

		if (Optional.ofNullable(productResponseList).isPresent()
				&& Optional.ofNullable(productResponseList).get().isEmpty()) {

			log.info("No Content-----");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(productResponseList, HttpStatus.OK);
	}
}
