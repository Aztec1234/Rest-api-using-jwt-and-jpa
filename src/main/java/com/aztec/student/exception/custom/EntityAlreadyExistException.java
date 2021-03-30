package com.aztec.student.exception.custom;

import lombok.Getter;

@Getter
public class EntityAlreadyExistException extends RuntimeException {

    private String message;

    public EntityAlreadyExistException(String message){
        this.message = message;
    }
}
