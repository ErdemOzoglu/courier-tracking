package com.erdemo.couriertracking.generic.exceptions;

import com.erdemo.couriertracking.generic.enums.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends BusinessException {

    public ItemNotFoundException(BaseErrorMessage message) {
        super(message);
    }
}
