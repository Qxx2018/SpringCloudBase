package com.itheima.sys.core.dto.jwt;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * jwt负载
 * @author XinXingQian 
 */
@Data
@Builder
public class PayLoad implements Serializable {

    private static final long serialVersionUID = -6011920002827434545L;
    /**
     * 账户号
     * 对应token的键account
     */
    private String account;

    /**
     * 登入密码
     * 对应token的键password
     */
    private String password;

    /**
     * 角色编码
     * 对应token的键roleCode
     */
    private List<String> roleCodeList;

    /**
     * 资源权限编码
     * 对应token的键resourceAuthorityCode
     */
    private List<String> resourceAuthorityCodeList;
}
