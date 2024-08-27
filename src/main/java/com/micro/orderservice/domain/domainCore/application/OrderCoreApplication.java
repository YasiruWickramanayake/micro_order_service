package com.micro.orderservice.domain.domainCore.application;

import com.micro.orderservice.common.dto.application.commonDto.OrderDto;
import com.micro.orderservice.common.dto.application.commonDto.OrderItemDto;
import com.micro.orderservice.common.dto.application.commonDto.OrderPaymentDto;
import com.micro.orderservice.common.dto.controller.requestAndResponse.OrderCreateRequest;
import com.micro.orderservice.domain.domainApplication.OrderDomainApplication;
import com.micro.orderservice.domain.domainCore.entity.Order;
import com.micro.orderservice.domain.domainCore.entity.OrderItem;
import com.micro.orderservice.domain.domainCore.entity.OrderPayment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderCoreApplication implements OrderDomainApplication {

    @Override
    public OrderDto createOrder(OrderCreateRequest orderCreateRequest){
        Order order =new Order(orderCreateRequest);
        return generateOrderDto(order);
    }

    @Override
    public OrderDto updateOrderForTheProductReservationSuccess(OrderDto orderDto) {
        Order order = new Order(orderDto);
        Order updatedOrder = order.updateOrderForProductReservationSuccess();
        return generateOrderDto(updatedOrder);
    }

    @Override
    public OrderDto updateOrderForTheProductReservationFail(OrderDto orderDto) {
        Order order = new Order(orderDto);
        Order updatedOrder = order.updateOrderForProductReservationFail();
        return generateOrderDto(updatedOrder);
    }

    @Override
    public OrderDto updateOrderForThePaymentSuccess(OrderDto orderDto) {
        Order order = new Order(orderDto);
        Order updatedOrder = order.updateOrderForPaymentSuccess();
        return generateOrderDto(updatedOrder);
    }

    @Override
    public OrderDto updateOrderForThePaymentFail(OrderDto orderDto) {
        Order order = new Order(orderDto);
        Order updatedOrder = order.updateOrderForPaymentFail();
        return generateOrderDto(updatedOrder);
    }

    @Override
    public OrderDto updateOrderForProductReleaseSuccess(OrderDto orderDto) {
        Order order = new Order(orderDto);
        Order updatedOrder = order.updateOrderForProductRelease();
        return generateOrderDto(updatedOrder);
    }


    public OrderDto generateOrderDto(Order order){
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .customerId(order.getCustomerId())
                .fullGrossAmount(order.getFullGrossAmount())
                .fullNetAmount(order.getFullNetAmount())
                .fullDiscountAmount(order.getFullDiscountAmount())
                .sagaId(order.getSagaId())
                .trackingId(order.getTrackingId())
                .status(order.getOrderStatus())
                .narration(order.getOrderNarration())
                .nextStatus(order.getOrderNextStatus())
                .nextStatusNarration(order.getOrderNextStatusNarration())
                .orderItems(this.getOrderItemDtoList(order.getOrderItems()))
                .orderPayments(this.getOrderPaymentDtoList(order.getOrderPayments()))
                .build();
    }

    private List<OrderItemDto> getOrderItemDtoList(List<OrderItem> orderItems){
        return orderItems.stream().map(orderItem ->
                OrderItemDto.builder()
                        .productId(orderItem.getProductId())
                        .productName(orderItem.getProductName())
                        .grossPrice(orderItem.getGrossPrice())
                        .netPrice(orderItem.getNetPrice())
                        .discount(orderItem.getDiscount())
                        .quantity(orderItem.getQuantity())
                        .build()

        ).collect(Collectors.toList());
    }

    private List<OrderPaymentDto> getOrderPaymentDtoList(List<OrderPayment> orderPayments){
        return orderPayments.stream().map(orderPayment ->
                OrderPaymentDto.builder()
                        .paymentMethod(orderPayment.getPaymentMethod())
                        .amount(orderPayment.getAmount())
                        .paymentStatus(orderPayment.getPaymentStatus())
                        .build()).collect(Collectors.toList());
    }
}
