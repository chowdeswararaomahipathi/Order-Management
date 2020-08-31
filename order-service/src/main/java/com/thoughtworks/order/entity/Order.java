package com.thoughtworks.order.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Order_ThoughtWorks")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String customerName;
	private Date orderDate;
	private String shippingAddress;
	private Float totalCost;
	private String orderItems;
	/*
	 * @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	 * 
	 * @JoinTable(name = "Order_Item_Mapping", inverseJoinColumns =
	 * { @JoinColumn(name = "Item_ID") }, joinColumns = {
	 * 
	 * @JoinColumn(name = "Order_ID") }) private Set<Product> orderItemList;
	 */
	 
	
	
	 
	
}
