package com.thoughtworks.order.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

	@Id
	@GeneratedValue
	private Long id;
	private String productName;
	private Long productCode;
	private Long availableQuantity;
	private Float price;
	
}
