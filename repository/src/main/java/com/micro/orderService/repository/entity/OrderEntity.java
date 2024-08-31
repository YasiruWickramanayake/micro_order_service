package com.micro.orderService.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "tracking_id", unique = true)
    private String trackingId;
    @Column(name = "saga_id", unique = true)
    private String sagaId;
    @Column(name = "gross_amount")
    private Double grossOrderAmount;
    @Column(name = "net_amount")
    private Double netOrderAmount;
    @Column(name = "discount_amount")
    private Double discountAmount;
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderItemsEntity> orderItemEntities;
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderPaymentEntity> orderPaymentEntities;
    @Column(name = "order_status")
    private Integer orderStatus;
    @Column(name = "narration")
    private String narration;
    @Column(name = "next_status")
    private Integer nextOrderStatus;
    @Column(name = "next_status_narration")
    private String nextStatusNarration;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name="created_user")
    private String createdUser;
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    @Column(name="last_updated_user")
    private String lastUpdatedUser;
}
