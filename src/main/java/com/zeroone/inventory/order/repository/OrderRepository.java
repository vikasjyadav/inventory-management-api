package com.zeroone.inventory.order.repository;

import com.zeroone.inventory.order.Entity.Order;
import com.zeroone.inventory.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order , Long> {

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderStatus = 'CREATED'")
    BigDecimal calculateTotalSales();

   /* @Query("SELECT COUNT FROM Order o WHERE o.orderStatus = 'CREATED'")
    List<Order> getCreatedOrders();*/

    long countByOrderStatus(OrderStatus orderStatus);

   /* @Query("SELECT o FROM Order o WHERE o.orderStatus = 'CANCELLED'")
    List<Order> getCanceledOrders();*/

}

