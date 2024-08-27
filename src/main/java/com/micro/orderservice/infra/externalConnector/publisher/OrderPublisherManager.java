package com.micro.orderservice.infra.externalConnector.publisher;

import com.micro.orderservice.common.dto.application.commonDto.OrderDto;

public interface OrderPublisherManager {

    public void publishOrderMessage(OrderDto orderDto);
}
