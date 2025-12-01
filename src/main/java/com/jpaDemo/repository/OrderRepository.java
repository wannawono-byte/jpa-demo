package com.jpaDemo.repository;

import com.jpaDemo.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 기본 쿼리 메서드
    List<Order> findByMemberId(Long memberId);

    // N+1 발생하는 버전
    List<Order> findAllWithItems(); // 그냥 findAll() 하면 N+1 터짐!

    // N+1 해결 1 - fetch join
    @Query("select distinct o from Order o join fetch o.orderItems oi where o.id = :orderId")
    Optional<Order> findWithItemsById(Long orderId);

    // N+1 해결 2 - @EntityGraph (실무에서 제일 많이 씀!)
    @EntityGraph(attributePaths = {"orderItems", "member"})
    @Query("select o from Order o")
    List<Order> findAllWithGraph();
}