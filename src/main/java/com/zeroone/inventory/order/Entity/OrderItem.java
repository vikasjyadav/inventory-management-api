package com.zeroone.inventory.order.Entity;

import com.zeroone.inventory.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "order_id",
            nullable = false)
    private Order order;

    @Positive
    @Column(nullable = false)
    private int quantity;

    @Positive
    @Column(nullable = false)
    private BigDecimal price;
}
