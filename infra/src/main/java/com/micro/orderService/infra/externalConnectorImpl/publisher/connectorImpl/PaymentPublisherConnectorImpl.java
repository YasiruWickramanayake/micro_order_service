package com.micro.orderService.infra.externalConnectorImpl.publisher.connectorImpl;


import com.micro.orderService.infra.externalConnector.publisher.PaymentPublisherConnector;
import com.micro.paymentService.PaymentInitiateRequest;
import com.micro.paymentService.PaymentInitiateResponse;
import com.micro.paymentService.PaymentServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class PaymentPublisherConnectorImpl implements PaymentPublisherConnector {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GrpcClient("payment-initiate-service")
    PaymentServiceGrpc.PaymentServiceBlockingStub paymentServiceBlockingStub;
    @Override
    public void initiatePayment(PaymentInitiateRequest paymentInitiateRequest) {
        try{
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonMessage = objectMapper.writeValueAsString(paymentInitiationMessage);
//            kafkaTemplate.send("init-payment", jsonMessage);

            PaymentInitiateResponse paymentInitiateResponse =
                    paymentServiceBlockingStub.paymentInitiate(paymentInitiateRequest);
        }catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }
    }
}
