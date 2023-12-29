package com.itheima.oauth.certification.models;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.sys.core.enums.GenderEnum;
import com.itheima.common.model.BaseModelWithTenant;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统-管理平台-用户
 * @author XinXingQian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_web_user")
public class SysWebUserModel extends BaseModelWithTenant<SysWebUserModel> {
    private static final long serialVersionUID = 764806738944097645L;

    /**
     * 对应的账号id
     */
    @TableField(value = "account_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long accountId;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 性别 M男F女 {@link GenderEnum}
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * 头像
     */
    @TableField(value = "face_pic")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long facePic;
}
