package com.erdemo.couriertracking.delivery.entity;

import com.erdemo.couriertracking.generic.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DELIVERY_MOVEMENT")
@Getter
@Setter
public class DeliveryMovement extends BaseEntity {

    @GeneratedValue(generator = "DeliveryMovement")
    @SequenceGenerator(name = "DeliveryMovement", sequenceName = "DELIVERY_ID_SEQ")
    @Id
    private Long id;

    @Column(name = "ID_DELIVERY")
    private Long deliveryId;

    private Double latitude;

    private Double longitude;

    @Column(name = "MOVEMENT_DATE", columnDefinition = "TIMESTAMP")
    private LocalDateTime movementDate;

}
