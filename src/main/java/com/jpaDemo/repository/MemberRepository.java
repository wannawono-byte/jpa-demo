package com.jpaDemo.repository;

import com.jpaDemo.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 회원 찾기 (실무에서 99% 씀)
    Optional<Member> findByEmail(String email);

    // 회원 + 주문 모두 한 번에 가져오기 (N+1 방지)
    @Query("select m from Member m join fetch m.orders o join fetch o.orderItems where m.id = :id")
    Optional<Member> findWithOrdersAndItems(@Param("id") Long id);

    // 이메일로 회원 + 주문까지 한 번에 (EntityGraph 버전 - 실무에서 더 자주 씀!)
    @EntityGraph(attributePaths = {"orders", "orders.orderItems"})
    @Query("select m from Member m where m.email = :email")
    Optional<Member> findWithOrdersByEmail(@Param("email") String email);
}