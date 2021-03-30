package com.aztec.student.exception.custom;

import lombok.Getter;

import java.io.File;

@Getter
public class FileStorageException extends RuntimeException {

    private String message;
    private Throwable cause;
    public FileStorageException(String message){
        this.message=message;
    }

    public FileStorageException(String message, Throwable cause) {
        this.message=message;
        this.cause=cause;
    }
}
