package com.micro.orderservice.common.dto.infra.externalConnector.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReservedOrderItem {

    private Integer productId;
    private Integer quantity;
    private Double amount;

}
