package com.thoughtworks.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequest {

	@ApiModelProperty(value = "productName", required = true)
	private String productName;
	@ApiModelProperty(value = "quantity", required = true)
	private Long quantity;
	@ApiModelProperty(value = "price of the product ", hidden = true)
	private Float price;
	@ApiModelProperty(value = "code of the product ", hidden = true)
	private Long productCode;
	
}
