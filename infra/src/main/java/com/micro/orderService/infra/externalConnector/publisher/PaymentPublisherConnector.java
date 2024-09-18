package com.micro.orderService.infra.externalConnector.publisher;

import com.micro.paymentService.PaymentInitiateRequest;


public interface PaymentPublisherConnector {

    public void initiatePayment(PaymentInitiateRequest paymentInitiateRequest);
}
