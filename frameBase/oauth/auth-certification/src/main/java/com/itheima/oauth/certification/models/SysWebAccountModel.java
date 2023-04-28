package com.itheima.oauth.certification.models;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.common.model.BaseModelWithTenant;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统-管理平台-账户
 * @author XinXingQian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_web_account")
public class SysWebAccountModel extends BaseModelWithTenant<SysWebAccountModel> {

    private static final long serialVersionUID = -3093921955279413210L;
    /**
     * 登入账号
     */
    @TableField(value = "account")
    private String account;

    /**
     * 登录账号密码
     */
    @TableField(value = "password")
    private String password;
}
