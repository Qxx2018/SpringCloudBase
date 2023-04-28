package com.itheima.oauth.certification.business.service;

import com.itheima.oauth.certification.dto.PermissionDTO;

/**
 * 认证用户-权限信息-服务接口
 * <br/>实现认证用户的所持有的权限信息操作
 * @author XinXingQian
 */
public interface PermissionInformationService {
    /**
     * 获取认证用户的权限信息
     * @param account 账号
     * @return {@link PermissionDTO}
     */
    PermissionDTO getPermissionByAccount(String account);

}
