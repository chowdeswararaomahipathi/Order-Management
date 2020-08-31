package com.thoughtworks.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {

	private Long id;
	private String productName;
	private Long productCode;
	private Long availableQuantity;
	private Double price;
}
