package com.zeroone.inventory.order.dto;

import com.zeroone.inventory.order.OrderStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusResponse {

    private Long id;
     private OrderStatus status;
}
