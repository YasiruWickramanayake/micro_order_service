package com.micro.orderService.coreDomain.entity;



import com.micro.orderService.commons.dto.controller.requestAndResponse.PaymentDetails;
import com.micro.orderService.commons.utils.enums.OrderStatus;
import com.micro.orderService.commons.utils.exceptions.OrderCreateException;
import lombok.Getter;

@Getter
public class OrderPayment {
    private final Integer paymentMethod;
    private final Double amount;
    private final String paymentStatus;

    public OrderPayment(PaymentDetails paymentDetails) {
        this.paymentMethod = paymentDetails.getPaymentMethod();
        this.amount = paymentDetails.getAmount();
        this.paymentStatus = this.getPaymentStatus(this.paymentMethod);
    }

    public OrderPayment invalidateOrderPayment(){
       return new OrderPayment(this.paymentMethod, this.getAmount(),
               OrderStatus.ORDER_PAYMENT_INVALIDATE.getOrderStatusMessage());
    }

    public OrderPayment rejectOrderPayment(){
        return new OrderPayment(this.paymentMethod, this.getAmount(),
                OrderStatus.ORDER_PAYMENT_REJECT.getOrderStatusMessage());
    }

    public OrderPayment updatePaymentStatusForPaymentSuccess(){
        return new OrderPayment(this.paymentMethod, this.getAmount(),
                OrderStatus.ORDER_PAYMENT_SUCCESS.getOrderStatusMessage());
    }

    public OrderPayment(Integer paymentMethod, Double amount, String paymentStatus) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    private String getPaymentStatus(Integer paymentMethod){
        String paymentStatus;
        switch (paymentMethod){
            case 1,2 : {
                paymentStatus = OrderStatus.ORDER_PAYMENT_INITIATED.getOrderStatusMessage();
                break;
            }
            case 3 :{
                paymentStatus = OrderStatus.ORDER_PAYMENT_SUCCESS.getOrderStatusMessage();
                break;
            } default :{
                throw new OrderCreateException(OrderStatus.ORDER_INITIATION_FAILED.getOrderStatusCode(),
                        "invalid payment method");
            }
        }
        return paymentStatus;
    }
}
