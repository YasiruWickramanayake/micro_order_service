package com.micro.orderservice.common.dto.controller.requestAndResponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateResponse {
    private Integer statusCode;
    private String message;
    private String trackingCode;
}
