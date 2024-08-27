package com.micro.orderservice.domain.domainApplication;

import com.micro.orderservice.common.dto.application.commonDto.OrderDto;
import com.micro.orderservice.common.dto.controller.requestAndResponse.OrderCreateRequest;

public interface OrderDomainApplication {

    public OrderDto createOrder(OrderCreateRequest orderCreateRequest);

    public OrderDto updateOrderForTheProductReservationSuccess(OrderDto orderDto);
    public OrderDto updateOrderForTheProductReservationFail(OrderDto orderDto);
    public OrderDto updateOrderForThePaymentSuccess(OrderDto orderDto);

    public OrderDto updateOrderForThePaymentFail(OrderDto orderDto);
    public OrderDto updateOrderForProductReleaseSuccess(OrderDto orderDto);
}
