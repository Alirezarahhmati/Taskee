package com.example.tasklee2;

import java.io.DataOutputStream ;
import java.io.IOException ;
import java.net.Socket ;

public class Request {

    private final String requestCode ;
    public Request(String requestCode)
    {
        this.requestCode = requestCode ;
    }

    public String getRequestCode() {
        return requestCode;
    }
}
