package com.micro.orderService.commons.dto.infra.externalConnector.input;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReleaseSuccessMessage {
    private String sagaId;
}
