package com.thoughtworks.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thoughtworks.order.entity.Order;

public interface IOrderRepository extends JpaRepository<Order, Long>{

}
