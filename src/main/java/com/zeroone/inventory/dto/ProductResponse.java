package com.zeroone.inventory.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private Double price;

    private Integer quantity;

    private LocalDateTime createdAt;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime updatedAt;
}
