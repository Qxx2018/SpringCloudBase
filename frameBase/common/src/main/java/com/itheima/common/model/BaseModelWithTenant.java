package com.itheima.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 适用租户的Entity
 * @author XinXingQian
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseModelWithTenant<T extends Model<T>> extends BaseModel<T> implements Serializable {
    private static final long serialVersionUID = 270231771789447301L;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    protected String tenantId;
}
