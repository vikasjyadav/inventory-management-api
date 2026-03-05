package com.zeroone.inventory.order.mapper;

import com.zeroone.inventory.order.Entity.Order;
import com.zeroone.inventory.order.Entity.OrderItem;
import com.zeroone.inventory.order.dto.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public OrderStatusResponse toResponse(Order order){
        if (order == null){
            return null;
        }
        return OrderStatusResponse.builder()
                .id(order.getId())
                .status(order.getOrderStatus())
                .build();
    }

    public OrderResponse toResponses(Order order) {
        if (order == null){
            return null;
        }

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : order.getItems()){
            orderItemResponses.add(this.toOrderItemResponse(orderItem));
        }
         return OrderResponse.builder()
                 .id(order.getId())
                 .orderDate(order.getOrderDate())
                 .items(orderItemResponses)
                 .totalAmount(order.getTotalAmount())
                 .orderStatus(order.getOrderStatus())
                 .build();
}

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem){
        if (orderItem == null){
            return null;
        }
        BigDecimal quantity = BigDecimal.valueOf(orderItem.getQuantity());

        return OrderItemResponse.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .subTotal(quantity.multiply(orderItem.getPrice()))
                .build();
    }
}
