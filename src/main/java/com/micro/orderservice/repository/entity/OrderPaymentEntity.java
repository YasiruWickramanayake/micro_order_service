package com.micro.orderservice.repository.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "order_payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderEntity orderEntity;
    @Column(name ="status")
    private String paymentStatus;
    @Column(name = "payment_method")
    private Integer paymentMethod;
    @Column(name = "payable_amount")
    private Double payableAmount;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name="created_user")
    private String createdUser;
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    @Column(name="last_updated_user")
    private String lastUpdatedUser;
}
