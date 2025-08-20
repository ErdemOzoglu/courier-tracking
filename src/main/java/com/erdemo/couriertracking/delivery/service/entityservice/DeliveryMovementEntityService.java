package com.erdemo.couriertracking.delivery.service.entityservice;

import com.erdemo.couriertracking.delivery.dao.DeliveryMovementDao;
import com.erdemo.couriertracking.delivery.entity.DeliveryMovement;
import com.erdemo.couriertracking.generic.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeliveryMovementEntityService extends BaseEntityService<DeliveryMovement, DeliveryMovementDao> {

    public DeliveryMovementEntityService(DeliveryMovementDao dao) {
        super(dao);
    }

    public DeliveryMovement findLastDeliveryMovementByDeliveryId(Long deliveryId){
        return getDao().findFirstByDeliveryIdOrderByMovementDateDesc(deliveryId);
    }

    public List<DeliveryMovement> findAllByDeliveryIdOrderByMovementDate(Long deliveryId){
        return getDao().findAllByDeliveryIdOrderByMovementDate(deliveryId);
    }
}
