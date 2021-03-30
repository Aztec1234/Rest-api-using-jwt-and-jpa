package com.aztec.student.exception.custom;

import lombok.Getter;

@Getter
public class MyFileNotFoundException extends RuntimeException{

    private String message;
    private Throwable cause;
    public MyFileNotFoundException(String message){
        this.message=message;
    }

    public MyFileNotFoundException(String message, Throwable cause) {
      this.message=message;
      this.cause=cause;

    }
}
