package com.erdemo.couriertracking.delivery.dao;

import com.erdemo.couriertracking.delivery.entity.DeliveryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryMovementDao extends JpaRepository<DeliveryMovement, Long> {

    List<DeliveryMovement> findAllByDeliveryIdOrderByMovementDate(Long deliveryId);

    DeliveryMovement findFirstByDeliveryIdOrderByMovementDateDesc(Long deliveryId);

}
