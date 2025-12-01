package com.jpaDemo.controller;

import com.jpaDemo.domain.Member;
import com.jpaDemo.domain.Order;
import com.jpaDemo.domain.OrderItem;
import com.jpaDemo.repository.OrderRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final EntityManager em;

    @PostMapping("/sample")
    public String createSample() {
        Member member = new Member("user@jpaDemo.com", "jpaDemo");
        em.persist(member);

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setMember(member);

        OrderItem item1 = new OrderItem();
        item1.setProductName("HAIGHT2");
        item1.setPrice(350000);
        item1.setQuantity(1);
        order.addOrderItem(item1);

        orderRepository.save(order);
        return "샘플 데이터 생성 완료! ID = " + order.getId();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderRepository.findWithItemsById(id)
                .orElseThrow();
    }

    @GetMapping("/graph")
    public List<Order> getAllWithGraph() {
        return orderRepository.findAllWithGraph(); // N+1 없이 한 번에!
    }
}