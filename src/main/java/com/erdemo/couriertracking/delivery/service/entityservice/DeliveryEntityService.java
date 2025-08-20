package com.erdemo.couriertracking.delivery.service.entityservice;

import com.erdemo.couriertracking.delivery.dao.DeliveryDao;
import com.erdemo.couriertracking.delivery.entity.Delivery;
import com.erdemo.couriertracking.delivery.enums.EnumDeliveryStatus;
import com.erdemo.couriertracking.generic.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeliveryEntityService extends BaseEntityService<Delivery, DeliveryDao> {

    public DeliveryEntityService(DeliveryDao dao) {
        super(dao);
    }

    public Delivery findActiveDeliveryByCourierId(Long courierId){
        return getDao().findByCourierIdAndDeliveryStatus(courierId, EnumDeliveryStatus.ACTIVE);
    }

    public List<Delivery> findAllCompletedDeliveryListByCourierId(Long courierId){
        return getDao().findAllByCourierIdAndDeliveryStatus(courierId, EnumDeliveryStatus.COMPLETED);
    }
}
