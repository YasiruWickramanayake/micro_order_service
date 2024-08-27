package com.micro.orderservice.repository.daoImpl;

import com.micro.orderservice.common.dto.application.commonDto.OrderDto;
import com.micro.orderservice.common.dto.application.commonDto.OrderItemDto;
import com.micro.orderservice.common.dto.application.commonDto.OrderPaymentDto;
import com.micro.orderservice.repository.dao.OrderRepository;
import com.micro.orderservice.repository.entity.OrderEntity;
import com.micro.orderservice.repository.entity.OrderItemsEntity;
import com.micro.orderservice.repository.entity.OrderPaymentEntity;
import com.micro.orderservice.repository.jpa.OrderRepositoryJpa;
import com.micro.orderservice.common.utils.enums.OrderStatus;
import com.micro.orderservice.common.utils.exceptions.OrderCreateException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderRepositoryJpa orderRepositoryJpa;

    @Transactional(rollbackOn = Exception.class)
    public void saveOrder(OrderDto orderDto) {
        try {
            OrderEntity orderEntity = this.createOrder(orderDto);
            orderRepositoryJpa.save(orderEntity);
        } catch (RuntimeException ex) {
            throw new OrderCreateException(OrderStatus.ORDER_INITIATION_FAILED.getOrderStatusCode(),
                    OrderStatus.ORDER_INITIATION_FAILED.getOrderStatusMessage());
        }
    }

    @Override
    public OrderDto orderFindBySagaId(String sagaId) {
        try {
            Optional<OrderEntity> orderBySagaId = orderRepositoryJpa.findBySagaId(sagaId);
            if (orderBySagaId.isEmpty()) {
                throw new RuntimeException();
            }
            return this.mapOrderEntityToDto(orderBySagaId.get());
        } catch (RuntimeException ex) {
            throw new RuntimeException();
        }
    }

    private OrderEntity createOrder(OrderDto orderDto) {
        try {
            java.util.Date date = new java.util.Date();

            OrderEntity orderEntity = OrderEntity.builder()
                    .orderId(orderDto.getOrderId())
                    .trackingId(orderDto.getTrackingId())
                    .grossOrderAmount(orderDto.getFullGrossAmount())
                    .netOrderAmount(orderDto.getFullNetAmount())
                    .discountAmount(orderDto.getFullDiscountAmount())
                    .customerId(orderDto.getCustomerId())
                    .sagaId(orderDto.getSagaId())
                    .orderStatus(orderDto.getStatus())
                    .narration(orderDto.getNarration())
                    .nextOrderStatus(orderDto.getNextStatus())
                    .nextStatusNarration(orderDto.getNextStatusNarration())
                    .createdUser("system")
                    .createdDate(new Date(date.getTime()))
                    .lastUpdatedUser("system")
                    .lastUpdateDate(new Date(date.getTime()))
                    .build();

            orderEntity.setOrderItemEntities(createOrderItems(orderEntity, orderDto.getOrderItems()));
            orderEntity.setOrderPaymentEntities(createOrderPayments(orderEntity, orderDto.getOrderPayments()));
            return orderEntity;
        } catch (RuntimeException ex) {
            throw new OrderCreateException(OrderStatus.ORDER_INITIATION_FAILED.getOrderStatusCode(),
                    OrderStatus.ORDER_INITIATION_FAILED.getOrderStatusMessage() +
                            "when initiate the order "
                            + ex.getMessage());
        }
    }

    private List<OrderItemsEntity> createOrderItems(OrderEntity orderEntity, List<OrderItemDto> cartDetails) {
        try {
            java.util.Date date = new java.util.Date();

            return cartDetails.stream().map(cartDetail ->
                    OrderItemsEntity.builder()
                            .productId(cartDetail.getProductId())
                            .productName(cartDetail.getProductName())
                            .orderEntity(orderEntity)
                            .grossAmount(cartDetail.getGrossPrice())
                            .netAmount(cartDetail.getNetPrice())
                            .discountAmount(cartDetail.getDiscount())
                            .quantity(cartDetail.getQuantity())
                            .createdUser("system")
                            .createdDate(new Date(date.getTime()))
                            .lastUpdatedUser("system")
                            .lastUpdateDate(new Date(date.getTime()))
                            .build()
            ).collect(Collectors.toList());
        } catch (RuntimeException ex) {
            throw new OrderCreateException(OrderStatus.ORDER_INITIATION_FAILED.getOrderStatusCode(),
                    OrderStatus.ORDER_INITIATION_FAILED.getOrderStatusMessage() +
                            " when initiate the Order items : ex :" +
                            ex.getMessage());
        }
    }

    private List<OrderPaymentEntity> createOrderPayments(OrderEntity orderEntity, List<OrderPaymentDto> orderPaymentDtos) {
        java.util.Date date = new java.util.Date();
        return orderPaymentDtos.stream().map(paymentDetail -> {

            return OrderPaymentEntity.builder()
                    .orderEntity(orderEntity)
                    .payableAmount(paymentDetail.getAmount())
                    .paymentMethod(paymentDetail.getPaymentMethod())
                    .paymentStatus(paymentDetail.getPaymentStatus())
                    .createdUser("system")
                    .createdDate(new Date(date.getTime()))
                    .lastUpdatedUser("system")
                    .lastUpdateDate(new Date(date.getTime()))
                    .build();
        }).collect(Collectors.toList());
    }

    private OrderDto mapOrderEntityToDto(OrderEntity orderEntity) {
        return OrderDto.builder()
                .orderId(orderEntity.getOrderId())
                .trackingId(orderEntity.getTrackingId())
                .sagaId(orderEntity.getSagaId())
                .customerId(orderEntity.getCustomerId())
                .fullGrossAmount(orderEntity.getGrossOrderAmount())
                .fullNetAmount(orderEntity.getNetOrderAmount())
                .status(orderEntity.getOrderStatus())
                .narration(orderEntity.getNarration())
                .nextStatus(orderEntity.getNextOrderStatus())
                .nextStatusNarration(orderEntity.getNextStatusNarration())
                .fullDiscountAmount(orderEntity.getDiscountAmount())
                .orderItems(mapOrderItemEntityToDto(orderEntity.getOrderItemEntities()))
                .orderPayments(mapOrderPaymentEntityToDto(orderEntity.getOrderPaymentEntities()))
                .build();
    }

    private List<OrderItemDto> mapOrderItemEntityToDto(List<OrderItemsEntity> orderItemsEntities) {
        return orderItemsEntities.stream().map(orderItemsEntity ->
                OrderItemDto.builder()
                        .productId(orderItemsEntity.getProductId())
                        .productName(orderItemsEntity.getProductName())
                        .grossPrice(orderItemsEntity.getGrossAmount())
                        .netPrice(orderItemsEntity.getNetAmount())
                        .discount(orderItemsEntity.getDiscountAmount())
                        .quantity(orderItemsEntity.getQuantity())
                .build()).collect(Collectors.toList());
    }

    private List<OrderPaymentDto> mapOrderPaymentEntityToDto(List<OrderPaymentEntity> orderPaymentEntities) {
        return orderPaymentEntities.stream().map(orderPaymentEntity -> OrderPaymentDto.builder()
                .paymentMethod(orderPaymentEntity.getPaymentMethod())
                .amount(orderPaymentEntity.getPayableAmount())
                .paymentStatus(orderPaymentEntity.getPaymentStatus())
                .build()).collect(Collectors.toList());
    }
}
