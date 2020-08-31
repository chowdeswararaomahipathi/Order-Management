package com.thoughtworks.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItem {
	
	    private Long id;
		private String productName;
		private Long productCode;
		//private Long availableQuantity;
		private Float price;
		
}
