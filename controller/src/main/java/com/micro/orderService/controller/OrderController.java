package com.micro.orderService.controller;


import com.micro.orderService.applicationInputPlugin.primary.OrderService;
import com.micro.orderService.commons.dto.controller.requestAndResponse.OrderCreateRequest;
import com.micro.orderService.commons.dto.controller.requestAndResponse.OrderCreateResponse;
import com.micro.orderService.commons.dto.infra.externalConnector.input.*;
import com.micro.orderService.commons.utils.enums.ResponseCodes;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public @ResponseBody OrderCreateResponse createOrder(@RequestBody OrderCreateRequest orderCreateRequest){
        String orderTrackingId = orderService.createOrder(orderCreateRequest);
        if(orderTrackingId != null){
            return OrderCreateResponse.builder()
                    .statusCode(ResponseCodes.ORDER_CREATION_SUCCESS.getResponseCode())
                    .message(ResponseCodes.ORDER_CREATION_SUCCESS.getResponseMessage())
                    .trackingCode(orderTrackingId)
                    .build();
        }else{
            return OrderCreateResponse.builder()
                    .statusCode(ResponseCodes.ORDER_CREATION_FAILED.getResponseCode())
                    .message(ResponseCodes.ORDER_CREATION_FAILED.getResponseMessage())
                    .build();
        }
    }

    @PostMapping("/inventory-reservation-success")
    public String updateOrderByProdReservationSuccess(@RequestBody ProductReservationSuccessMessage productReservationSuccessMessage){
       orderService.orderUpdateBasedOnProductReservationSuccess(productReservationSuccessMessage);
        return "product reservation success";
    }

    @PostMapping("/inventory-reservation-fail")
    public String updateOrderByProdReservationFail(@RequestBody ProductReservationFailMessage productReservationFailMessage){
        orderService.orderUpdateBasedOnProductReservationFail(productReservationFailMessage);
        return "product reservation fail";
    }

    @PostMapping("/payment-success")
    public String updateOrderPaymentSuccess(@RequestBody PaymentSuccessMessage paymentSuccessMessage){
        orderService.orderUpdateBasedOnPaymentSuccess(paymentSuccessMessage);
        return "order updated based on payment success";
    }

    @PostMapping("/payment-fail")
    private String updateOrderPaymentSuccess(@RequestBody PaymentFailMessage paymentFailMessage){
        orderService.orderUpdateBasedOnPaymentFail(paymentFailMessage);
        return "order updated based on payment fail";
    }

    @PostMapping("/reservation-release-success")
    private String updateOrderByReservationSuccess(@RequestBody ProductReleaseSuccessMessage productReleaseSuccessMessage){
        orderService.orderUpdateBasedOnProductReleaseSuccess(productReleaseSuccessMessage);
        return "order updated based on product release";
    }




}
