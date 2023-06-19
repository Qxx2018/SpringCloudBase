package com.itheima.common.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.common.constants.Constant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础Entity
 * @author 10445
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseModel<T extends Model<T>>  extends Model<T> implements Serializable {

    private static final long serialVersionUID = 5361209634636523305L;
    /**
     * 表id
     * 格式化时转string
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long id;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * 状态位
     * @TableLogoc
     */
    @TableLogic
    @TableField(value = "deleted")
    protected Integer deleted = Constant.NOT_DELETE;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE, update = "NOW()")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updatedTime;

    /**
     * 创建者
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    protected String createdBy;

    /**
     * 更新者
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    protected String updatedBy;

    /**
     * 版本
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT_UPDATE, update = "%s+1")
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long version;
}
