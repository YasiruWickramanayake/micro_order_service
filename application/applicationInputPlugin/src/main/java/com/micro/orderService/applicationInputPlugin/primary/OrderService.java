package com.micro.orderService.applicationInputPlugin.primary;

import com.micro.orderService.commons.dto.controller.requestAndResponse.OrderCreateRequest;
import com.micro.orderService.commons.dto.infra.externalConnector.input.*;


public interface OrderService {

    public String createOrder(OrderCreateRequest orderCreateRequest);

    public void orderUpdateBasedOnProductReservationSuccess(ProductReservationSuccessMessage productReservationSuccessMessage);

    public void orderUpdateBasedOnProductReservationFail(ProductReservationFailMessage productReservationFailMessage);
    public void orderUpdateBasedOnPaymentSuccess(PaymentSuccessMessage paymentSuccessMessage);

    public void orderUpdateBasedOnPaymentFail(PaymentFailMessage paymentFailMessage);

    public void orderUpdateBasedOnProductReleaseSuccess(ProductReleaseSuccessMessage productReleaseSuccessMessage);

}
