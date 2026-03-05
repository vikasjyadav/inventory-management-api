package com.zeroone.inventory.order.dto;

import lombok.*;


import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesSummaryResponse {

    private BigDecimal totalSales;

    private long totalOrders;

    private long createdOrders;

    private long canceledOrders;
}
