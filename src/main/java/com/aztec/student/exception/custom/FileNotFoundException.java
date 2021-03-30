package com.aztec.student.exception.custom;

import lombok.Getter;

@Getter
public class FileNotFoundException extends RuntimeException{

    private String message;
    private Throwable cause;
    public FileNotFoundException(String message){
        this.message=message;
    }

    public FileNotFoundException(String message, Throwable cause) {
      this.message=message;
      this.cause=cause;

    }
}
