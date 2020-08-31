package com.thoughtworks.order.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

	private Long id;
	private String customerName;
	private Date orderDate;
	private String shippingAddress;
	private Float totalCost;
	private List<OrderItem> orderItemList;
}
