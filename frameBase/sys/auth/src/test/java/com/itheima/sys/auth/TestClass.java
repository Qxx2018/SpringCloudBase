package com.itheima.sys.auth;

import com.itheima.SysAuthApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {SysAuthApplication.class,TestClass.class})
@RunWith(SpringRunner.class)
@Slf4j
public class TestClass {
    @Test
    public void t1() {
        log.error("111");
    }
}
