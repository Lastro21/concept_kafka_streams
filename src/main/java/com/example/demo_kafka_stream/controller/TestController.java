package com.example.demo_kafka_stream.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private int count;

    @RequestMapping(value = "/hello")
    public String rrt() {
        System.out.println(count);
        count++;
        return String.valueOf(count);
    }

}
