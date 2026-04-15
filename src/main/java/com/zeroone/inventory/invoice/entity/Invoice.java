package com.zeroone.inventory.invoice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "purchase_id")
    private Long purchaseId;

    @NotNull
    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @NotNull
    private LocalDateTime invoiceDate;

    @NotNull
    private Double totalAmount;
}