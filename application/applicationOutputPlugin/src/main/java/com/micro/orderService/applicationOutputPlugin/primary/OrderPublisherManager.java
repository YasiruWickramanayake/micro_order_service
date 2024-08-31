package com.micro.orderService.applicationOutputPlugin.primary;


import com.micro.orderService.commons.dto.application.commonDto.OrderDto;

public interface OrderPublisherManager {

    public void publishOrderMessage(OrderDto orderDto);
}
