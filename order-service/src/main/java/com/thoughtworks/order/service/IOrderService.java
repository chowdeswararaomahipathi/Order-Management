package com.thoughtworks.order.service;

import java.util.List;

import com.thoughtworks.order.dto.OrderRequest;
import com.thoughtworks.order.dto.OrderResponse;

public interface IOrderService {

	List<OrderResponse> getOrders();
	void createOrder(List<OrderRequest> orderRequestList);
}
