package com.micro.orderservice.repository.jpa;

import com.micro.orderservice.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepositoryJpa extends JpaRepository<OrderEntity, Integer> {

    @Override
    <S extends OrderEntity> S save(S entity);


    Optional<OrderEntity> findBySagaId(String sagaId);
}
