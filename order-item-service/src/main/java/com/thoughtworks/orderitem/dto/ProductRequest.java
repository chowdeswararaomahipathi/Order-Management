package com.thoughtworks.orderitem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequest {

	@ApiModelProperty(value = "productName", required = true)
	private String productName;
	@ApiModelProperty(value = "productCode", required = true)
	private Long productCode;
	@ApiModelProperty(value = "availableQuantity", required = true)
	private Long availableQuantity;
	@ApiModelProperty(value = "price", required = true)
	private Float price;
}
