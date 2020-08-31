package com.thoughtworks.order.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderedProduct {

	@Id
	@GeneratedValue
	private Long id;
	private String productName;
	private Long quantity;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="order_id")
	private Order order;
	
}
