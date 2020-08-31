package com.thoughtworks.order.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtworks.order.dto.OrderRequest;
import com.thoughtworks.order.dto.OrderResponse;
import com.thoughtworks.order.dto.ProductRequest;
import com.thoughtworks.order.dto.ProductResponse;
import com.thoughtworks.order.errorresponse.ErrorObject;
import com.thoughtworks.order.service.IOrderService;
import com.thoughtworks.order.service.ProductCacheService;
import com.thoughtworks.order.userexception.ProductNotFound;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("orders")
@Validated
@Log4j2
public class OrderController {

	@Autowired
	private IOrderService iOrderService;

	@Autowired
	private ProductCacheService productCacheService;

	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Create an order")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Order Created"),
			@ApiResponse(code = 400, message = "Invalid order") })
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestBody List<OrderRequest> orderRequestList) throws Exception {

			if (orderRequestList == null || orderRequestList.isEmpty()) {
				return new ResponseEntity<>(new ErrorObject("Input data can't be null or empty"), HttpStatus.BAD_REQUEST);
			}
			if (Optional.ofNullable(orderRequestList).isPresent()) {

				boolean anyEmptyCustomerName = orderRequestList.parallelStream()
						.anyMatch(orderRequest -> orderRequest.getCustomerName() == null
								|| orderRequest.getCustomerName().isEmpty());
				if (anyEmptyCustomerName) {
					return new ResponseEntity<>(new ErrorObject("Customer Can't be empty or null"), HttpStatus.BAD_REQUEST);
				}
				boolean anyEmptyShippingAddress = orderRequestList.parallelStream()
						.anyMatch(orderRequest -> orderRequest.getShippingAddress() == null
								|| orderRequest.getShippingAddress().isEmpty());
				if (anyEmptyShippingAddress) {
					return new ResponseEntity<>(new ErrorObject("Shipping Address can't be null or empty"),
							HttpStatus.BAD_REQUEST);
				}
				for (OrderRequest orderReq : orderRequestList) {
					boolean anyEmptyProductName = orderReq.getOrderItemList().parallelStream()
							.anyMatch(itemRequest -> itemRequest.getProductName() == null
									|| itemRequest.getProductName().isEmpty());
					if (anyEmptyProductName) {
						return new ResponseEntity<>(new ErrorObject("Product Name can't be null or empty"),
								HttpStatus.BAD_REQUEST);

					}
					boolean anyEmptyOrNegativeQuantity = orderReq.getOrderItemList().parallelStream()
							.anyMatch(itemRequest -> itemRequest.getQuantity() == null || itemRequest.getQuantity() == 0
									|| itemRequest.getQuantity() < 0);
					if (anyEmptyOrNegativeQuantity) {
						return new ResponseEntity<>(new ErrorObject("Quantity can't be Zero or negative"),
								HttpStatus.BAD_REQUEST);

					}
				}

			}

			List<ProductResponse> productList = loadProducts();

			isProductAvailable(orderRequestList, productList);

			log.info("before calling the iOrderService for creating order");
			iOrderService.createOrder(orderRequestList);
			return new ResponseEntity<>(HttpStatus.CREATED);

	}
	

	@ApiOperation(value = "Get all orders")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retreieved all orders", response = OrderResponse.class, responseContainer = "List"),
			@ApiResponse(code = 204, message = "No Content") })
	@GetMapping
	public ResponseEntity<?> getOrders() {

		log.info("before calling the iOrderService for getting orders");
		List<OrderResponse> orderResponseList = iOrderService.getOrders();
		log.info("after calling the iOrderService for getting orders");
		Optional<List<OrderResponse>> orderListOptional = Optional.ofNullable(orderResponseList);

		if (orderListOptional.isPresent() && orderListOptional.get().isEmpty()) {

			log.info("No Content------");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(orderResponseList, HttpStatus.OK);
	}

	// To load all products from order-item-service
	private List<ProductResponse> loadProducts() {
		log.info("before calling the iOrderService for getting products");
		return productCacheService.getProducts();
	}

	// To check if selected products list is available in product table
	private void isProductAvailable(List<OrderRequest> orderRequestList, List<ProductResponse> productList) {

		log.info("Checking if the selected products are available-----");

		for (OrderRequest orderRequest : orderRequestList) {
			List<ProductRequest> orderItemList = orderRequest.getOrderItemList();

			long count = orderItemList.parallelStream()
					.filter(orderItem -> productList.parallelStream()
							.anyMatch(product -> product.getProductName().equals(orderItem.getProductName())
									&& orderItem.getQuantity() <= product.getAvailableQuantity()))
					.count();

			if (count != orderItemList.size()) {
				throw new ProductNotFound("Product not found");
			}
		}

	}
	
}