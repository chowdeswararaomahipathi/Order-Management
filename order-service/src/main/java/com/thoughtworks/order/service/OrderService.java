package com.thoughtworks.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.thoughtworks.order.dto.OrderItem;
import com.thoughtworks.order.dto.OrderRequest;
import com.thoughtworks.order.dto.OrderResponse;
import com.thoughtworks.order.dto.ProductRequest;
import com.thoughtworks.order.dto.ProductResponse;
import com.thoughtworks.order.entity.Order;
import com.thoughtworks.order.repository.IOrderRepository;


@Service
public class OrderService implements IOrderService {

	@Autowired
	private IOrderRepository iOrderRepository;
	
	@Autowired
	private ProductCacheService productCacheService;

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void createOrder(List<OrderRequest> orderRequestList) {

		  List<Order> orderList = new ArrayList<>();
		 
		  orderRequestList.parallelStream().forEach(orderRequest -> {
			  Order order = convertToOrder(orderRequest);
			  orderList.add(order);
		  });
		iOrderRepository.saveAll(orderList);

	}
	

	@Override
	@HystrixCommand(fallbackMethod = "getFallBackForProducts")
	public List<OrderResponse> getOrders() {

		List<OrderResponse> orderResponseList = new ArrayList<OrderResponse>();
		List<ProductResponse> products = productCacheService.getProducts();
		List<Order> orderList = iOrderRepository.findAll();
		
		orderList.parallelStream().forEach(order ->{
			OrderResponse orderResponse = objectMapper.convertValue(order, OrderResponse.class);
			List<OrderItem> orderItemList = new ArrayList<>();
			if(Optional.ofNullable(products).isPresent() && order.getOrderItems() != null && order.getOrderItems().length() > 0)
			{
				String[] itemIds = order.getOrderItems().split(",");
				for(String itemId: itemIds)
				{
					Optional<ProductResponse> item = products.stream().filter(product -> product.getId().equals(Long.parseLong(itemId))).findAny();
				    if(item.isPresent()) {
				    	OrderItem orderItem = new OrderItem();
				    	orderItem.setId(item.get().getId());
				    	orderItem.setPrice(item.get().getPrice().floatValue());
				    	orderItem.setProductCode(item.get().getProductCode());
				    	orderItem.setProductName(item.get().getProductName());
				    	orderItemList.add(orderItem);
				    }
				}
			}
			orderResponse.setOrderItemList(orderItemList);
			orderResponseList.add(orderResponse);
		});
		

		return orderResponseList;
	}

	@HystrixCommand(fallbackMethod = "getFallBackForProducts")
	private  Order convertToOrder(OrderRequest orderRequest) {
		Float totalCost=0f;
		StringBuilder itemIds= new StringBuilder();
	    List<ProductResponse> products = productCacheService.getProducts();
		Order order = objectMapper.convertValue(orderRequest, Order.class);
		for(ProductRequest item: orderRequest.getOrderItemList())
		{
			Optional<ProductResponse> productObject = products.stream().filter(prodcut -> prodcut.getProductName().equalsIgnoreCase(item.getProductName())).findAny();
		    if(productObject.isPresent()) {
		    	itemIds.append(productObject.get().getId()+",");
		    	totalCost = (float) (totalCost + (item.getQuantity()*productObject.get().getPrice()));
		    }
		}
		if(itemIds.toString().length() > 0)
		{
			order.setOrderItems(itemIds.subSequence(0, itemIds.length()-1).toString());
		}
		order.setTotalCost(totalCost);
		order.setOrderDate(new Date());
		return order;
	}
	
	public List<ProductResponse> getFallBackForProducts(){
		
		List<ProductResponse> products = new ArrayList<ProductResponse>();
		ProductResponse product = new ProductResponse();
		product.setId(0L);
		product.setProductName("Dummy product");
		product.setProductCode(000L);
		product.setAvailableQuantity(0L);
		return products;
	}


	
}
