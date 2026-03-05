package com.zeroone.inventory.order.service;

import com.zeroone.inventory.entity.Product;
import com.zeroone.inventory.exception.ResourceNotFoundException;
import com.zeroone.inventory.order.Entity.Order;
import com.zeroone.inventory.order.Entity.OrderItem;
import com.zeroone.inventory.order.OrderStatus;
import com.zeroone.inventory.order.dto.OrderItemRequestDTO;
import com.zeroone.inventory.order.dto.OrderRequestDTO;
import com.zeroone.inventory.order.dto.SalesSummaryResponse;
import com.zeroone.inventory.order.dto.UpdateOrderStatusRequest;
import com.zeroone.inventory.order.repository.OrderRepository;
import com.zeroone.inventory.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Product product;
    private OrderRequestDTO request;
    private OrderItemRequestDTO item;
    private Order order;

    @BeforeEach
    void setUp() {

        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1000.0);
        product.setQuantity(10);

        item = new OrderItemRequestDTO();
        item.setProductId(1L);
        item.setQuantity(2);

        request = new OrderRequestDTO();
        request.setItems(List.of(item));

        order = new Order();
        order.setId(1L);
        order.setOrderStatus(OrderStatus.CREATED);
    }

    @Test
    void createOrder_success() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(orderRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.createOrder(request);

        assertNotNull(result);
        assertEquals(8, product.getQuantity());

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void createOrder_insufficientStock() {

        product.setQuantity(1);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(IllegalArgumentException.class,
                () -> orderService.createOrder(request));

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_productNotFound() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> orderService.createOrder(request));

        verify(orderRepository, never()).save(any());
    }

    @Test
    void updateOrderStatus_cancel_success() {

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2);

        order.setItems(List.of(item));

        UpdateOrderStatusRequest req = new UpdateOrderStatusRequest();
        req.setStatus(OrderStatus.CANCELLED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderRepository.save(any()))
                .thenReturn(order);

        Order updated = orderService.updateOrderStatus(1L, req);

        assertEquals(OrderStatus.CANCELLED, updated.getOrderStatus());
        verify(productRepository, times(1)).save(product);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void updateOrderStatus_orderNotFound() {

        UpdateOrderStatusRequest req = new UpdateOrderStatusRequest();
        req.setStatus(OrderStatus.CANCELLED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.updateOrderStatus(1L, req));
    }

    @Test
    void getAllOrders_success() {

        when(orderRepository.findAll())
                .thenReturn(List.of(order));

        List<Order> orders = orderService.getAllOrders();

        assertEquals(1, orders.size());
    }

    @Test
    void getOrderById_success() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getOrderById_notFound() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.getOrderById(1L));
    }

    @Test
    void getTotalSalesSummary_success() {

        when(orderRepository.calculateTotalSales())
                .thenReturn(BigDecimal.valueOf(2000));

        when(orderRepository.count())
                .thenReturn(5L);

        when(orderRepository.countByOrderStatus(OrderStatus.CREATED))
                .thenReturn(3L);

        when(orderRepository.countByOrderStatus(OrderStatus.CANCELLED))
                .thenReturn(2L);

        SalesSummaryResponse response = orderService.getTotalSalesSummary();

        assertEquals(BigDecimal.valueOf(2000), response.getTotalSales());
        assertEquals(5, response.getTotalOrders());
        assertEquals(3, response.getCreatedOrders());
        assertEquals(2, response.getCanceledOrders());
    }

    @Test
    void getTotalSalesSummary_nullSales() {

        when(orderRepository.calculateTotalSales())
                .thenReturn(null);

        when(orderRepository.count())
                .thenReturn(0L);

        when(orderRepository.countByOrderStatus(OrderStatus.CREATED))
                .thenReturn(0L);

        when(orderRepository.countByOrderStatus(OrderStatus.CANCELLED))
                .thenReturn(0L);

        SalesSummaryResponse response = orderService.getTotalSalesSummary();

        assertEquals(BigDecimal.ZERO, response.getTotalSales());
    }
}