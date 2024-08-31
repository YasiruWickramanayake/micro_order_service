package com.micro.orderService.repository.dao;


import com.micro.orderService.commons.dto.application.commonDto.OrderDto;

public interface OrderRepository {
    public void saveOrder(OrderDto orderDto);

    public OrderDto orderFindBySagaId(String sagaId);


}
