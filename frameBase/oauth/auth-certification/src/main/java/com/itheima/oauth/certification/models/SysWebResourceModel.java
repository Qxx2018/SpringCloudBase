package com.itheima.oauth.certification.models;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.common.model.BaseModelWithTenant;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统-管理平台-菜单资源表
 * @author XinXingQian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_web_resource")
public class SysWebResourceModel extends BaseModelWithTenant<SysWebResourceModel> {
    private static final long serialVersionUID = 4727470156813991417L;

    /**
     * 所属菜单id
     */
    @TableField(value = "menu_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;

    /**
     * 资源分类 ADD DELETE UPDATE QUERY
     */
    @TableField(value = "resource_category")
    private String resourceCategory;

    /**
     * 资源名称
     */
    @TableField(value = "resource_name")
    private String resourceName;

    /**
     * 资源访问url-请求访问的api地址 a/b/c
     */
    @TableField(value = "resource_url")
    private String resourceUrl;

    /**
     * 资源编码-全表唯一
     */
    @TableField(value = "resource_code")
    private String resourceCode;

    /**
     * 资源等级
     */
    @TableField(value = "resource_level")
    private Integer resourceLevel;

    /**
     * 同级别下排序
     */
    @TableField(value = "resource_sort")
    private Integer resourceSort;

    /**
     * 资源图标id
     */
    @TableField(value = "resource_icon_id") 
    @JsonSerialize(using = ToStringSerializer.class)
    private Long resourceIconId;

    /**
     * 显示或隐藏 显示1 隐藏0
     */
    @TableField(value = "hidden")
    private Integer hidden;
}
