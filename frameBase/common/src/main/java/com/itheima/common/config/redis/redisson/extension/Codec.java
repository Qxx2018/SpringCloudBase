package com.itheima.common.config.redis.redisson.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author XinXingQian
 */
@Data
@Component
public class Codec implements Serializable {

    private static final long serialVersionUID = -2873127339653422135L;
    @JsonProperty(value = "class")
    @Value("${sc.redisson.codec.clazz}")
    String cls;
}
