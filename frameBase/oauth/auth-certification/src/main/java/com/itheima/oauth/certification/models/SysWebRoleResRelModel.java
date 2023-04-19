package com.itheima.oauth.certification.models;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.common.model.BaseModelWithTenant;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统-管理平台-角色-资源权限-关联-表
 * @author XinXingQian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_web_role_res_rel")
public class SysWebRoleResRelModel extends BaseModelWithTenant<SysWebRoleResRelModel> {

    private static final long serialVersionUID = 5801813890205818070L;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 资源权限id-一个角色多个资源权限
     */
    @TableField(value = "resource_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long resourceId;
}
