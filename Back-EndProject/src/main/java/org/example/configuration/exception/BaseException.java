package org.example.configuration.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.configuration.http.BaseResponseCode;

import java.util.Arrays;

@Slf4j
@Getter
public class BaseException extends AbstractBaseException {

    private static final long serialVersionUID = 8342235231880246631L;

    public BaseException(){}

    public BaseException(BaseResponseCode responseCode){
        this.responseCode = responseCode;
    }

    public BaseException(BaseResponseCode responseCode,String[] args){
        this.responseCode = responseCode;
        this.args = args;
    }
//    public BaseException(String msg) {
//        super(msg);
//    }


}