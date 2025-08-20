package com.erdemo.couriertracking.couriertracking.service;

import com.erdemo.couriertracking.store.dto.StoreInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationControlServiceTest {

    @InjectMocks
    private LocationControlService locationControlService;

    @Test
    void shouldIsEnteredIsTrue_WhenTheLocationsAreSame() {

        double latitude = 40.986106;
        double longitude = 29.1161293;

        StoreInfoDto storeInfoDto = mock(StoreInfoDto.class);
        when(storeInfoDto.getLatitude()).thenReturn(latitude);
        when(storeInfoDto.getLongitude()).thenReturn(longitude);

        boolean result = locationControlService.isEntered(storeInfoDto, longitude, latitude);

        assertTrue(result);
    }

    @Test
    void shouldIsEnteredIsFalse_WhenTheLocationsAreFarFromEachOther() {

        double latitude = 41.986106;
        double longitude = 28.1161293;

        StoreInfoDto storeInfoDto = mock(StoreInfoDto.class);
        when(storeInfoDto.getLatitude()).thenReturn(40.986106);
        when(storeInfoDto.getLongitude()).thenReturn(29.1161293);

        boolean result = locationControlService.isEntered(storeInfoDto, longitude, latitude);

        assertFalse(result);
    }

    @Test
    void shouldIsReenteredIsTrue_WhenDifferenceIsLessThanOneMinute() {

        LocalDateTime time1 = LocalDateTime.of(2025, 4, 5, 12, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2025, 4, 5, 12, 0, 59);

        boolean result = locationControlService.isReentered(time1, time2);

        assertTrue(result);

    }

    @Test
    void shouldIsReenteredIsTrue_WhenDifferenceIsOneMinute() {

        LocalDateTime time1 = LocalDateTime.of(2025, 4, 5, 12, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2025, 4, 5, 12, 1, 0);

        boolean result = locationControlService.isReentered(time1, time2);

        assertTrue(result);
    }

    @Test
    void shouldIsReenteredIsFalse_WhenDifferenceIsMoreThanOneMinute() {

        LocalDateTime time1 = LocalDateTime.of(2025, 4, 5, 12, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2025, 4, 5, 12, 1, 1);

        boolean result = locationControlService.isReentered(time1, time2);

        assertFalse(result);
    }

    @Test
    void shouldGetClosestStoreToTheLocation() {

        double latitude = 40.986106;
        double longitude = 29.1161293;

        Optional<StoreInfoDto> result = locationControlService.getClosestStoreToTheLocation(longitude, latitude);

        assertTrue(result.isPresent());
    }

    @Test
    void shouldNotGetClosestStoreToTheLocation() {

        double latitude = 99.99;
        double longitude = 99.99;

        Optional<StoreInfoDto> result = locationControlService.getClosestStoreToTheLocation(longitude, latitude);

        assertFalse(result.isPresent());
    }
}