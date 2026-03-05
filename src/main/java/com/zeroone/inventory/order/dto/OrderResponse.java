package com.zeroone.inventory.order.dto;

import com.zeroone.inventory.order.Entity.OrderItem;
import com.zeroone.inventory.order.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private LocalDateTime orderDate;

    private List<OrderItemResponse> items = new ArrayList<>();

    private BigDecimal totalAmount;

    private OrderStatus orderStatus;

}
