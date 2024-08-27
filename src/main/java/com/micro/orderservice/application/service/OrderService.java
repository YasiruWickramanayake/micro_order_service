package com.micro.orderservice.application.service;

import com.micro.orderservice.common.dto.controller.requestAndResponse.OrderCreateRequest;
import com.micro.orderservice.common.dto.infra.externalConnector.input.*;

public interface OrderService {

    public String createOrder(OrderCreateRequest orderCreateRequest);

    public void orderUpdateBasedOnProductReservationSuccess(ProductReservationSuccessMessage productReservationSuccessMessage);

    public void orderUpdateBasedOnProductReservationFail(ProductReservationFailMessage productReservationFailMessage);
    public void orderUpdateBasedOnPaymentSuccess(PaymentSuccessMessage paymentSuccessMessage);

    public void orderUpdateBasedOnPaymentFail(PaymentFailMessage paymentFailMessage);

    public void orderUpdateBasedOnProductReleaseSuccess(ProductReleaseSuccessMessage productReleaseSuccessMessage);

}
