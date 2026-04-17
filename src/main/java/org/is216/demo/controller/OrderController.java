package org.is216.demo.controller;

import org.is216.demo.model.Order;
import org.is216.demo.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public Map<String, Object> getOrders() throws Exception {
        InetAddress host = InetAddress.getLocalHost();
        
        List<Order> dbOrders = orderRepository.findAll();

        return Map.of(
                "pod_name", host.getHostName(),
                "pod_ip", host.getHostAddress(),
                "data", dbOrders
        );
    }

    @PostMapping
    public Map<String, String> createOrder(@RequestBody Order newOrder) {
        Order orderToSave = new Order(newOrder.getCustomer(), newOrder.getItem(), "Chờ bếp làm");
        orderRepository.save(orderToSave);
        
        return Map.of("status", "success", "message", "Đã chốt đơn thành công vào Database!");
    }
}