package com.itheima.sys.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.common.model.BaseModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 角色资源关联
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role_resource_rel")
public class SysRoleResourceRelModel extends BaseModel<SysRoleResourceRelModel> {

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 资源id
     */
    @TableField(value = "resource_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long resourceId;


}
