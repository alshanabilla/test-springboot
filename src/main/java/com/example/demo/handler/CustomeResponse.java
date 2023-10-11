package com.example.demo.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomeResponse {
    // GET -> status code, message & data
    public static ResponseEntity<Object> generate(HttpStatus httpStatus, String message, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", httpStatus);
        map.put("message", message);
        map.put("results", data);
        return new ResponseEntity<Object> (map, httpStatus);
    }

    // POST & DELETE -> status code, message 
    public static ResponseEntity<Object> generate(HttpStatus httpStatus, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", httpStatus);
        map.put("message", message);
        return new ResponseEntity<Object> (map, httpStatus);
    }
}
