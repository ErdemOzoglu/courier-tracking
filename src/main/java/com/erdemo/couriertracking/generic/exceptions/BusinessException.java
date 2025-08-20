package com.erdemo.couriertracking.generic.exceptions;

import com.erdemo.couriertracking.generic.enums.BaseErrorMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@RequiredArgsConstructor
@Data
public class BusinessException extends RuntimeException{

    private final BaseErrorMessage baseErrorMessage;
}
