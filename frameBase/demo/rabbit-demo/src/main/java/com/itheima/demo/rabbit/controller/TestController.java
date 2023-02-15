package com.itheima.demo.rabbit.controller;

import com.itheima.demo.rabbit.producer.BProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/middleware/rabbitmq/test")
@Slf4j
public class TestController {
    @Resource
    private BProducer bProducer;
    @GetMapping("/pushB/add")
    public Boolean pushBAdd() {
        bProducer.testPushToMqBAdd();
        return Boolean.TRUE;
    }
    @GetMapping("/pushB2/add")
    public Boolean pushB2Add() {
        bProducer.testPushToMqB2Add();
        return Boolean.TRUE;
    }
    @GetMapping("/pushB3/add")
    public Boolean pushB3Add() {
        bProducer.testPushToMqB3Add();
        return Boolean.TRUE;
    }
    @GetMapping("/pushB/deleted")
    public Boolean pushBDeleted() {
        bProducer.testPushToMqBDeleted();
        return Boolean.TRUE;
    }
}
