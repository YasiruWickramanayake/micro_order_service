package com.micro.orderService.infra.externalConnectorImpl.publisher.connectorImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.orderService.commons.dto.infra.externalConnector.output.PaymentInitiationMessage;
import com.micro.orderService.infra.externalConnector.publisher.PaymentPublisherConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class PaymentPublisherConnectorImpl implements PaymentPublisherConnector {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public void initiatePayment(PaymentInitiationMessage paymentInitiationMessage) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(paymentInitiationMessage);
            kafkaTemplate.send("init-payment", jsonMessage);
        }catch (RuntimeException ex){

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
