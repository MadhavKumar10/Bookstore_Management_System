package com.bookstore.service;

import com.bookstore.entity.Order;
import com.bookstore.entity.User;
import com.bookstore.repository.OrderRepository;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired // Or @Inject
    private EntityManager entityManager;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<Order> getOrdersByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }

    @Transactional 
    public Order placeOrder(Order order) {
        User user = userRepository.findByEmail(order.getUser().getEmail()).orElseThrow(
                () -> new RuntimeException("User not found")
        ); 
        order.setUser(user); 
        Order orderToPersist = entityManager.merge(order); 
        return orderRepository.save(orderToPersist); 
    }

    // Retrieve a single order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }
}
