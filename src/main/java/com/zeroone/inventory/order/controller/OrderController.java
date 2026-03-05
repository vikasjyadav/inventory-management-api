package com.zeroone.inventory.order.controller;

import com.zeroone.inventory.order.Entity.Order;
import com.zeroone.inventory.order.dto.OrderRequestDTO;
import com.zeroone.inventory.order.dto.OrderResponse;
import com.zeroone.inventory.order.dto.OrderStatusResponse;
import com.zeroone.inventory.order.dto.UpdateOrderStatusRequest;
import com.zeroone.inventory.order.mapper.OrderMapper;
import com.zeroone.inventory.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderMapper orderMapper;

    private final OrderService orderService;

    public OrderController(OrderMapper orderMapper, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
        Order order = orderService.createOrder(orderRequestDTO);
        OrderResponse responses = orderMapper.toResponses(order);
        return new ResponseEntity<>(responses,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        List<OrderResponse> orderResponses = new ArrayList<>();
        for(Order order : orderService.getAllOrders()){
            orderResponses.add( orderMapper.toResponses(order));
        }
        return new ResponseEntity<>(orderResponses,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id){

        return new ResponseEntity<>(orderMapper.toResponses(orderService.getOrderById(id)),HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderStatusResponse > updateOrderStatus(@PathVariable Long id , @Valid @RequestBody UpdateOrderStatusRequest request){
        OrderStatusResponse response = orderMapper.toResponse(orderService.updateOrderStatus(id, request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

