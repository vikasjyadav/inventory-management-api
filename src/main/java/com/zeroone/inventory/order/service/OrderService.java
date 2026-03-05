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
import jakarta.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setTotalAmount(BigDecimal.ZERO);

        for (OrderItemRequestDTO items : orderRequestDTO.getItems()) {
            Product product = productRepository.findById(items.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + items.getProductId()));

            if (product.getQuantity() < items.getQuantity()){
                throw new IllegalArgumentException("Insufficient stock for product id: " + items.getProductId());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setQuantity(items.getQuantity());
            BigDecimal price = BigDecimal.valueOf(product.getPrice());
            orderItem.setPrice(price);

            BigDecimal total = price.multiply(BigDecimal.valueOf(items.getQuantity()));
            order.setTotalAmount(order.getTotalAmount().add(total));

            order.getItems().add(orderItem);

            product.setQuantity(product.getQuantity() - items.getQuantity());
        }
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, @Valid UpdateOrderStatusRequest request){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order Not Found with Order Id : " + orderId));

        OrderStatus currentStatus = order.getOrderStatus();
        if((currentStatus != OrderStatus.CREATED)){
            throw new IllegalStateException("Order Status Cannot be Changed Once Finalized");
        }

        if(request.getStatus() == null){
            throw new IllegalArgumentException("Status cannot be null..");
        }

        if(currentStatus == request.getStatus()){
            throw new IllegalStateException("Order is already in the requested status..");
        }

        OrderStatus newStatus = request.getStatus();

        if (newStatus == OrderStatus.CANCELLED){
            for(OrderItem item : order.getItems()){
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        }
        order.setOrderStatus(newStatus);
        return orderRepository.save(order);
    }


    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Not Found With OrderId : " + id));
    }

    public SalesSummaryResponse getTotalSalesSummary(){

        BigDecimal bigDecimal = orderRepository.calculateTotalSales();
        if (bigDecimal == null){
            bigDecimal = BigDecimal.ZERO;
        }
        long allOrders = orderRepository.count();
        long createdOrders =orderRepository.countByOrderStatus(OrderStatus.CREATED);
        long canceledOrders = orderRepository.countByOrderStatus(OrderStatus.CANCELLED);

        return SalesSummaryResponse.builder()
                .totalSales(bigDecimal)
                .totalOrders(allOrders)
                .createdOrders(createdOrders)
                .canceledOrders(canceledOrders)
                .build();
    }

}
