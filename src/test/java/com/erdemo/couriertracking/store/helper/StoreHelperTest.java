package com.erdemo.couriertracking.store.helper;

import com.erdemo.couriertracking.generic.exceptions.BusinessException;
import com.erdemo.couriertracking.store.dto.StoreInfoDto;
import com.erdemo.couriertracking.store.enums.StoreErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class StoreHelperTest {

    @Test
    void shouldConvertToStoreInfoDto() throws JsonProcessingException {

        String json = getStoreInfoAsJSON();

        List<StoreInfoDto> storeInfoDtoList = StoreHelper.convertToStoreInfoDto(json);

        assertEquals(5L, storeInfoDtoList.size());

    }

    @Test
    void shouldGetStoreInfoDtoList() {

        List<StoreInfoDto> storeInfoDtoList = StoreHelper.getStoreInfoDtoList("/static/stores.json");

        assertEquals(5, storeInfoDtoList.size());
    }

    @Test
    void shouldNotGetStoreInfoDtoList() {

        BusinessException businessException = assertThrows(BusinessException.class,
                () -> StoreHelper.getStoreInfoDtoList(""));

        assertEquals(StoreErrorMessage.COULD_NOT_READ_DATA, businessException.getBaseErrorMessage());
    }

    @Test
    void convertToStoreInfoDto() throws JsonProcessingException {

        String storeInfoAsJSON = getStoreInfoAsJSON();


        List<StoreInfoDto> storeInfoDtoList = StoreHelper.convertToStoreInfoDto(storeInfoAsJSON);

        assertEquals(5, storeInfoDtoList.size());
    }

    private String getStoreInfoAsJSON() {
        String json = "[\n" +
                "  {\n" +
                "    \"name\": \"Ataşehir MMM Migros\",\n" +
                "    \"lat\": 40.9923307,\n" +
                "    \"lng\": 29.1244229\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Novada MMM Migros\",\n" +
                "    \"lat\": 40.986106,\n" +
                "    \"lng\": 29.1161293\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Beylikdüzü 5M Migros\",\n" +
                "    \"lat\": 41.0066851,\n" +
                "    \"lng\": 28.6552262\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Ortaköy MMM Migros\",\n" +
                "    \"lat\": 41.055783,\n" +
                "    \"lng\": 29.0210292\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Caddebostan MMM Migros\",\n" +
                "    \"lat\": 40.9632463,\n" +
                "    \"lng\": 29.0630908\n" +
                "  }\n" +
                "]";
        return json;
    }
}