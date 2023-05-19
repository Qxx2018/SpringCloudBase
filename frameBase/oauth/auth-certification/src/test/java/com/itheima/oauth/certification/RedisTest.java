package com.itheima.oauth.certification;

import com.itheima.common.utils.RedisUtil;
import com.itheima.oauth.certification.dto.UserDetailsDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {
    @Resource
    private RedisUtil<UserDetailsDTO> redisUtil;
    @Test
    public void redis() {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setAccountId(10000L);
        redisUtil.set("aa", userDetailsDTO);
    }
}
