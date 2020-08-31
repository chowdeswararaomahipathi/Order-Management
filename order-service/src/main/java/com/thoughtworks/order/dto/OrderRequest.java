package com.thoughtworks.order.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {
     
	@ApiModelProperty(value = "customer name", required = true)
	@NotNull
	private String customerName;
	@NotNull
	@ApiModelProperty(value = "shippingAddress", required = true)
	private String shippingAddress;
	@NotNull
	@ApiModelProperty(value = "orderItemList", required = true)
	private List<ProductRequest> orderItemList;
	
}
