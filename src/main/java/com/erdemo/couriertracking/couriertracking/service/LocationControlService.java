package com.erdemo.couriertracking.couriertracking.service;

import com.erdemo.couriertracking.delivery.service.DistanceCalculatorService;
import com.erdemo.couriertracking.store.dto.StoreInfoDto;
import com.erdemo.couriertracking.store.helper.StoreHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
@Transactional
public class LocationControlService {

    private static final  String STORES_JSON_PATH = "/static/stores.json";;

    public boolean isReentered(LocalDateTime time, LocalDateTime lastMovementDate) {

        long secondsDiff = SECONDS.between(lastMovementDate, time);

        secondsDiff = Math.abs(secondsDiff);

        boolean isReentered = secondsDiff <= DistanceCalculatorService.MIN_SECOND_FOR_NEW_ENTRY;

        return isReentered;
    }

    public boolean isEntered(StoreInfoDto storeInfoDto, Double longitude, Double latitude){

        Double eachLatitude = storeInfoDto.getLatitude();
        Double eachLongitude = storeInfoDto.getLongitude();

        double distance = DistanceCalculatorService.distance(latitude, eachLatitude, longitude, eachLongitude);

        boolean isEnteredStore = distance <= DistanceCalculatorService.MIN_DISTANCE_FROM_STORE;

        return isEnteredStore;
    }

    public Optional<StoreInfoDto> getClosestStoreToTheLocation(Double longitude, Double latitude) {
        List<StoreInfoDto> storeInfoDtoList = StoreHelper.getStoreInfoDtoList(STORES_JSON_PATH);

        Optional<StoreInfoDto> storeInfoDtoOptional = storeInfoDtoList.stream()
                .filter(storeInfoDto -> isEntered(storeInfoDto, longitude, latitude))
                .findFirst();

        return storeInfoDtoOptional;
    }
}
