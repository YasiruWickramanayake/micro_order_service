package com.micro.orderservice.infra.externalConnector.publisher.connectorI;

import com.micro.orderservice.common.dto.infra.externalConnector.output.PaymentInitiationMessage;

public interface PaymentPublisherConnector {

    public void initiatePayment(PaymentInitiationMessage paymentInitiationMessage);
}
