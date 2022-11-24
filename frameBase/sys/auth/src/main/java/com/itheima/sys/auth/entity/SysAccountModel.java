package com.itheima.sys.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.common.model.BaseModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 账户表
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_account")
public class SysAccountModel extends BaseModel<SysAccountModel> {
    /**
     * 登入账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 登录账号密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 登录手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;
}
