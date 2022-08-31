package com.itheima.sys.auth.convert;
import com.itheima.common.coredata.modules.sys.auth.ao.SysAccountAO;
import com.itheima.sys.auth.entity.SysAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author 10445
 */
@Mapper
public interface SysAccountConvert {
    SysAccountConvert SYS_ACCOUNT_CONVERT =
            Mappers.getMapper(SysAccountConvert.class);

    /**
     * sysAccountAoToEntity
     * @param ao
     * @return
     */
    SysAccountEntity sysAccountAoToEntity(SysAccountAO ao);
}
