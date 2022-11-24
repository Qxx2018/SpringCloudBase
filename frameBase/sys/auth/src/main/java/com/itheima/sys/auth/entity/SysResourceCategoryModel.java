package com.itheima.sys.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.common.model.BaseModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 资源分类
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_resource_category")
public class SysResourceCategoryModel extends BaseModel<SysResourceCategoryModel> {

    /**
     * 资源分类名
     */
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

}
