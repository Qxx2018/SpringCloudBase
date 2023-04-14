package com.itheima.oauth.certification.models;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.common.model.BaseModelWithTenant;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统-管理平台-角色
 * @author XinXingQian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_web_role")
public class SysWebRoleModel extends BaseModelWithTenant<SysWebRoleModel> {

    private static final long serialVersionUID = 3839363502674759366L;
    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    private String roleCode;

    /**
     * 角色类型
     */
    @TableField(value = "role_type")
    private String roleType;

    /**
     * 角色名字
     */
    @TableField(value = "role_name")
    private String roleName;
}
