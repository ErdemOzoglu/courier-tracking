package com.erdemo.couriertracking.couriertracking.service;

import com.erdemo.couriertracking.couriertracking.converter.CourierMapper;
import com.erdemo.couriertracking.couriertracking.dto.CourierLocationInfoDto;
import com.erdemo.couriertracking.couriertracking.dto.CourierSaveRequestDto;
import com.erdemo.couriertracking.couriertracking.entity.Courier;
import com.erdemo.couriertracking.couriertracking.service.entityservice.CourierEntityService;
import com.erdemo.couriertracking.delivery.entity.Delivery;
import com.erdemo.couriertracking.delivery.entity.DeliveryMovement;
import com.erdemo.couriertracking.delivery.enums.EnumDeliveryStatus;
import com.erdemo.couriertracking.delivery.service.DistanceCalculatorService;
import com.erdemo.couriertracking.delivery.service.entityservice.DeliveryEntityService;
import com.erdemo.couriertracking.delivery.service.entityservice.DeliveryMovementEntityService;
import com.erdemo.couriertracking.store.dto.StoreInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourierTrackingService {

    private final CourierEntityService courierEntityService;
    private final DeliveryEntityService deliveryEntityService;
    private final DeliveryMovementEntityService deliveryMovementEntityService;
    private final LocationControlService locationControlService;

    public Courier saveCourier(CourierSaveRequestDto courierSaveRequestDto){

        Courier courier = CourierMapper.INSTANCE.convertToCourier(courierSaveRequestDto);
        courier = courierEntityService.save(courier);

        return courier;
    }

    public void saveCourierPosition(CourierLocationInfoDto courierLocationInfoDto) {

        Long courierId = courierEntityService.findByCourierPlate(courierLocationInfoDto.getCourierPlate())
                .map(Courier::getId)
                .orElseThrow(() -> new EntityNotFoundException("Kurye bulunamadı"));

        Delivery activeDelivery = deliveryEntityService.findActiveDeliveryByCourierId(courierId);

        if (Objects.isNull(activeDelivery)){
            Delivery delivery = createNewActiveDeliveryForCourier(courierLocationInfoDto);
            createNewMovement(delivery.getId(), courierLocationInfoDto);

            return;
        }

        LocalDateTime lastMovementDate = getLastMovementDate(activeDelivery);
        createNewMovement(activeDelivery.getId(), courierLocationInfoDto);
        controlAndCompleteDelivery(courierLocationInfoDto, activeDelivery, lastMovementDate);

    }

    public Double getTotalDistanceOfCourier(String courierPlate) {

        Long courierId = courierEntityService.findByCourierPlate(courierPlate)
                .map(Courier::getId)
                .orElseThrow(() -> new EntityNotFoundException("Kurye bulunamadı"));

        List<Delivery> deliveryList = deliveryEntityService.findAllCompletedDeliveryListByCourierId(courierId);

        Double totalDistance = deliveryList.stream()
                .filter(delivery -> delivery.getDistance() != null)
                .map(delivery -> delivery.getDistance())
                .collect(Collectors.summingDouble(Double::doubleValue));

        return totalDistance;
    }

    private Delivery createNewActiveDeliveryForCourier(CourierLocationInfoDto courierLocationInfoDto) {

        Long courierId = courierEntityService.findByCourierPlate(courierLocationInfoDto.getCourierPlate())
                .map(Courier::getId)
                .orElseThrow(() -> new EntityNotFoundException("Kurye bulunamadı"));

        Delivery delivery = new Delivery();
        delivery.setCourierId(courierId);
        delivery.setDeliveryStatus(EnumDeliveryStatus.ACTIVE);

        delivery = deliveryEntityService.save(delivery);

        return delivery;
    }

    private DeliveryMovement createNewMovement(Long deliveryId, CourierLocationInfoDto courierLocationInfoDto) {

        DeliveryMovement deliveryMovement = new DeliveryMovement();
        deliveryMovement.setDeliveryId(deliveryId);
        deliveryMovement.setMovementDate(courierLocationInfoDto.getTime());
        deliveryMovement.setLongitude(courierLocationInfoDto.getLongitude());
        deliveryMovement.setLatitude(courierLocationInfoDto.getLatitude());

        return deliveryMovementEntityService.save(deliveryMovement);
    }

    private void controlAndCompleteDelivery(CourierLocationInfoDto courierLocationInfoDto, Delivery activeDelivery, LocalDateTime lastMovementDate) {

        Double longitude = courierLocationInfoDto.getLongitude();
        Double latitude = courierLocationInfoDto.getLatitude();
        LocalDateTime time = courierLocationInfoDto.getTime();

        boolean isReentered = locationControlService.isReentered(time, lastMovementDate);

        if (!isReentered){

            Optional<StoreInfoDto> storeInfoDtoOptional = locationControlService.getClosestStoreToTheLocation(longitude, latitude);

            if (storeInfoDtoOptional.isPresent()){
                StoreInfoDto storeInfoDto = storeInfoDtoOptional.get();

                Double totalDeliveryDistance = calculateTotalDeliveryDistance(activeDelivery.getId());

                activeDelivery.setDeliveryStatus(EnumDeliveryStatus.COMPLETED);
                activeDelivery.setDistance(totalDeliveryDistance);
                activeDelivery.setStoreName(storeInfoDto.getName());

                deliveryEntityService.save(activeDelivery);
            }

        }
    }

    private Double calculateTotalDeliveryDistance(Long deliveryId) {

        List<DeliveryMovement> deliveryMovementList = deliveryMovementEntityService.findAllByDeliveryIdOrderByMovementDate(deliveryId);

        double totalDistance = 0.0;
        for (int i = 0; i < deliveryMovementList.size() -1; i++){

            DeliveryMovement deliveryMovement1 = deliveryMovementList.get(i);
            DeliveryMovement deliveryMovement2 = deliveryMovementList.get(i+1);

            Double latitude1 = deliveryMovement1.getLatitude();
            Double longitude1 = deliveryMovement1.getLongitude();

            Double latitude2 = deliveryMovement2.getLatitude();
            Double longitude2 = deliveryMovement2.getLongitude();

            double distance = DistanceCalculatorService.distance(latitude1, latitude2, longitude1, longitude2);

            totalDistance = totalDistance + distance;
        }
        
        return totalDistance;
    }

    private LocalDateTime getLastMovementDate(Delivery activeDelivery) {

        DeliveryMovement lastMovement = deliveryMovementEntityService.findLastDeliveryMovementByDeliveryId(activeDelivery.getId());

        LocalDateTime lastMovementDate = lastMovement.getMovementDate();
        return lastMovementDate;
    }
}
