package com.itheima.oauth.certification.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 权限信息DTO
 * @author XinXingQian
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PermissionDTO implements Serializable {
    private static final long serialVersionUID = 835581556534188081L;

    /**
     * 账户信息
     */
    private Account account;

    /**
     * 角色
     */
    private List<Role> role;

    /**
     * 资源
     */
    private List<Resource> resources;
    /**
     * 菜单
     */
    private List<Menu> menus;

    /**
     * 账户信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Account {
        /**
         * 租户id
         */
        private String tenantId;
        /**
         * 账号id
         */
        private Long accountId;
        /**
         * 登录账号
         */
        private String account;
        /**
         * 密码
         */
        private String password;

    }
    /**
     * 角色
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Role {
        /**
         * 角色编码
         */
        private String roleCode;
        /**
         * 角色名称
         */
        private String roleName;
    }
    /**
     * 资源
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Resource {
        /**
         * 资源分类
         */
        private String resourceCategory;
        /**
         * 资源名称-业务操作名称
         */
        private String resourceName;
        /**
         * 资源访问url-请求访问的api地址
         */
        private String resourceUrl;
        /**
         *  资源编码
         */
        private String resourceCode;
    }
    /**
     * 菜单
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Menu {
        /**
         * 菜单id
         */
        private Long id;
        /**
         * 菜单名
         */
        private String menuName;
        /**
         * 菜单编码
         */
        private String menuCode;
    }

}
