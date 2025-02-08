package com.bookstore.controller;

import com.bookstore.entity.Order;
import com.bookstore.entity.User;
import com.bookstore.repository.OrderRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @GetMapping
    public List<Order> getOrdersByUser(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername(); // Extracted from JWT
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }
    

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    // Retrieve details of a single order
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id, Authentication authentication) {
        Order order = orderService.getOrderById(id);
        // Ensure the user is either an Admin or the owner of the order
        if (isAdmin(authentication) || order.getUser().getEmail().equals(authentication.getName())) {
            return ResponseEntity.ok(order);
        } else {
            // Return 403 Forbidden if the user is unauthorized
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> requestBody, Authentication authentication) {
        if (isAdmin(authentication)) {
            String status = requestBody.get("status");
            if (status == null) {
                return ResponseEntity.badRequest().body("Status is required");
            }
            orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok("Order status updated successfully!");
        } else {
            // Return 403 Forbidden if the user is not an Admin
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin can update order status");
        }
    }

    // Helper method to check if the user is an Admin
    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
