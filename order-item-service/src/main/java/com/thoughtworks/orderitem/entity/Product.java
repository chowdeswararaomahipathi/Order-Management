package com.thoughtworks.orderitem.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Entity
@Data
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
