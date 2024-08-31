package com.micro.orderService.infra.externalConnectorImpl.publisher.connectorImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.orderService.commons.dto.infra.externalConnector.output.ProductReleaseInitiationMessage;
import com.micro.orderService.commons.dto.infra.externalConnector.output.ProductionReservationInitiateMessage;
import com.micro.orderService.infra.externalConnector.publisher.ProductPublisherConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class ProductPublisherConnectorImpl implements ProductPublisherConnector {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void initiateProductReservation(ProductionReservationInitiateMessage productionReservationInitiateMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(productionReservationInitiateMessage);
            kafkaTemplate.send("init-item-reservation", jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initiateProductRelease(ProductReleaseInitiationMessage productReleaseInitiationMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(productReleaseInitiationMessage);
            kafkaTemplate.send("init-product-release", jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
