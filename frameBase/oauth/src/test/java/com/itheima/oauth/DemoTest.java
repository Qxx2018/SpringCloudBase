package com.itheima.oauth;

import com.itheima.OauthApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {OauthApplication.class,DemoTest.class})
@RunWith(SpringRunner.class)
@Slf4j
public class DemoTest {
    @Test
    public void t1() {
        log.error("111");
    }
}
