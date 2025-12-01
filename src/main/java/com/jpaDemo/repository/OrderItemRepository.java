package com.jpaDemo.repository;

import com.jpaDemo.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 특정 주문에 속한 모든 아이템 가져오기
    List<OrderItem> findByOrderId(Long orderId);

    // 상품명으로 검색 (예: "HAIGHT2" 포함된 주문아이템 찾기)
    List<OrderItem> findByProductNameContaining(String productName);

    // 가격대별 검색 (예: 30만원 이상 고가 선글라스만)
    List<OrderItem> findByPriceGreaterThanEqual(int price);

    // 주문아이템 + 주문 + 회원까지 한 번에 (면접에서 "이렇게도 할 수 있어요~" 보여주면 점수 업!)
    @Query("select oi from OrderItem oi join fetch oi.order o join fetch o.member where oi.id = :id")
    OrderItem findWithOrderAndMember(@Param("id") Long id);
}