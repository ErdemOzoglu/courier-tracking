package com.erdemo.couriertracking.store.helper;

import com.erdemo.couriertracking.generic.exceptions.BusinessException;
import com.erdemo.couriertracking.store.dto.StoreInfoDto;
import com.erdemo.couriertracking.store.enums.StoreErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class StoreHelper {

    protected static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static List<StoreInfoDto> getStoreInfoDtoList(String path){

        List<StoreInfoDto> storeInfoDtoList;
        try {
            String storeInfoAsJSON = getStoreInfoAsJSON(path);
            storeInfoDtoList = convertToStoreInfoDto(storeInfoAsJSON);
        } catch (IOException e){
            throw new BusinessException(StoreErrorMessage.COULD_NOT_READ_DATA);
        }

        return storeInfoDtoList;
    }

    public static List<StoreInfoDto> convertToStoreInfoDto(String storeInfoJson) throws JsonProcessingException {

        List<StoreInfoDto> storeList = objectMapper.readValue(storeInfoJson, new TypeReference<>() {});

        return storeList;
    }

    private static String getStoreInfoAsJSON(String path) throws IOException {

        InputStream is = StoreHelper.class.getResourceAsStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        StringBuilder storeInfoSB = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            storeInfoSB.append(line);
        }

        return storeInfoSB.toString();
    }
}
