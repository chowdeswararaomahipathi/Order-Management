package com.thoughtworks.orderitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thoughtworks.orderitem.entity.Product;

public interface IProductRepository extends JpaRepository<Product, Long>{

}
