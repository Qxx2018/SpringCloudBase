package com.itheima.oauth.certification.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.oauth.certification.mapper.SysWebUserMapper;
import com.itheima.oauth.certification.models.SysWebUserModel;
import com.itheima.oauth.certification.service.SysWebUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统-管理平台-用户-接口-实现
 * @author XinXingQian
 */
@Slf4j
@Service
public class SysWebUserServiceImpl extends ServiceImpl<SysWebUserMapper, SysWebUserModel> implements SysWebUserService {
}
