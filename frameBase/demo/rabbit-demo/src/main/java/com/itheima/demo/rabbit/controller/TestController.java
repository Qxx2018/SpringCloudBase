package com.itheima.demo.rabbit.controller;

import com.itheima.demo.rabbit.producer.MyProducer;
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
    private MyProducer myProducer;
    @GetMapping("/push")
    public Boolean push() {
        myProducer.testPushToMq();
        return Boolean.TRUE;
    }
}
