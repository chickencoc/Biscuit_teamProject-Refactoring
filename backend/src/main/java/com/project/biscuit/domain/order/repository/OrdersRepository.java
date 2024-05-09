package com.project.biscuit.domain.order.repository;

import com.project.biscuit.domain.order.model.OrderStatus;
import com.project.biscuit.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUser_noOrderByCreatedAtDesc(Long userNo);
    List<Orders> findByUser_noAndStatusOrderByCreatedAtDesc(Long userNo, OrderStatus orderStatus);

}
