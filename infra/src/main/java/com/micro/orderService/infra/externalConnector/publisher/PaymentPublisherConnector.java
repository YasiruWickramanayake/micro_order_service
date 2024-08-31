package com.micro.orderService.infra.externalConnector.publisher;


import com.micro.orderService.commons.dto.infra.externalConnector.output.PaymentInitiationMessage;

public interface PaymentPublisherConnector {

    public void initiatePayment(PaymentInitiationMessage paymentInitiationMessage);
}
