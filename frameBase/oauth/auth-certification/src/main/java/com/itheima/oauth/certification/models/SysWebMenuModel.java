package com.itheima.oauth.certification.models;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.common.model.BaseModelWithTenant;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统-管理平台-菜单
 * @author XinXingQian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_web_menu")
public class SysWebMenuModel extends BaseModelWithTenant<SysWebMenuModel> {
    private static final long serialVersionUID = -2869984645768862307L;

    /**
     * 父级菜单id
     */
    @TableField(value = "parent_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 菜单名
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 菜单编码
     */
    @TableField(value = "menu_code")
    private String menuCode;

    /**
     * 菜单等级
     */
    @TableField(value = "menu_level")
    private Integer menuLevel;

    /**
     * 同级下菜单排序
     */
    @TableField(value = "menu_sort")
    private Integer menuSort;

    /**
     * 菜单图标id
     */
    @TableField(value = "menu_icon_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuIconId;

    /**
     * 菜单url
     */
    @TableField(value = "menu_url")
    private String menuUrl;

    /**
     * 菜单层级树 a/b/c
     */
    @TableField(value = "menu_tree_path")
    private String menuTreePath;

    /**
     * 是否隐藏 显示1 隐藏0
     */
    @TableField(value = "hidden")
    private Integer hidden;

}
