package com.micro.orderService.commons.dto.controller.requestAndResponse;

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
