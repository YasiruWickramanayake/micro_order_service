package com.micro.orderservice.repository.dao;

import com.micro.orderservice.common.dto.application.commonDto.OrderDto;
import com.micro.orderservice.common.dto.controller.requestAndResponse.OrderCreateRequest;

public interface OrderRepository {
    public void saveOrder(OrderDto orderDto);

    public OrderDto orderFindBySagaId(String sagaId);


}
