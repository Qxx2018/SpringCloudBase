package com.itheima.sys.core.dto.jwt;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * header
 * @author XinXingQian 
 */
@Data
@Builder
public class Header implements Serializable {

    private static final long serialVersionUID = 6496466007362753779L;

    /**
     * headerType
     */
    private String headerType;
    /**
     * 加密方式
     */
    private String alg;
}
