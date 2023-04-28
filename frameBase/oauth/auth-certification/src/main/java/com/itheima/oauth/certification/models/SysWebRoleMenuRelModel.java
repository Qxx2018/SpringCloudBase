package com.itheima.oauth.certification.models;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.common.model.BaseModelWithTenant;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统-管理平台-角色-菜单-关联
 * @author XinXingQian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_web_role_menu_rel")
public class SysWebRoleMenuRelModel extends BaseModelWithTenant<SysWebRoleMenuRelModel> {

    private static final long serialVersionUID = -8350973327086250057L;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;

}
