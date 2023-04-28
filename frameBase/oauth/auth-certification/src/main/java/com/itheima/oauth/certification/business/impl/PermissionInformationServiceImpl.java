package com.itheima.oauth.certification.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.common.constants.Constant;
import com.itheima.oauth.certification.business.service.PermissionInformationService;
import com.itheima.oauth.certification.dto.PermissionDTO;
import com.itheima.oauth.certification.models.*;
import com.itheima.oauth.certification.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 认证用户-权限信息-服务实现
 * @author XinXingQian
 */
@Slf4j
@Service
public class PermissionInformationServiceImpl implements PermissionInformationService {
    @Resource
    private SysWebAccountService sysWebAccountService;
    @Resource
    private SysWebAccRoleRelService sysWebAccRoleRelService;
    @Resource
    private SysWebRoleService sysWebRoleService;
    @Resource
    private SysWebRoleMenuRelService sysWebRoleMenuRelService;
    @Resource
    private SysWebMenuService sysWebMenuService;
    @Resource
    private SysWebRoleResRelService sysWebRoleResRelService;
    @Resource
    private SysWebResourceService sysWebResourceService;

    /**
     * 获取认证用户的权限信息
     *
     * @param account 账号
     */
    @Override
    public PermissionDTO getPermissionByAccount(String account) {

        //账户信息
        SysWebAccountModel accountModel = sysWebAccountService.getOne(
                new LambdaQueryWrapper<SysWebAccountModel>()
                        .select(SysWebAccountModel::getId,SysWebAccountModel::getAccount,SysWebAccountModel::getPassword)
                        .eq(SysWebAccountModel::getDeleted, Constant.NOT_DELETE)
                        .eq(SysWebAccountModel::getAccount,account)
        );
        if (Objects.isNull(accountModel)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        PermissionDTO.Account perAccount = PermissionDTO.Account.builder().build();
        BeanUtil.copyProperties(accountModel,perAccount);
        //账户id
        Long accountId = accountModel.getId();
        //账户下绑定的角色id集合
        List<PermissionDTO.Role> perRoleList = new ArrayList<>();
        List<Long> roleIdList = Collections.emptyList();
        List<SysWebAccRoleRelModel> accRoleRelModelList = sysWebAccRoleRelService.list(
                new LambdaQueryWrapper<SysWebAccRoleRelModel>()
                        .select(SysWebAccRoleRelModel::getRoleId)
                        .eq(SysWebAccRoleRelModel::getDeleted,Constant.NOT_DELETE)
                        .eq(SysWebAccRoleRelModel::getAccountId,accountId)
        );
        if (CollUtil.isNotEmpty(accRoleRelModelList)) {
            roleIdList = accRoleRelModelList.stream().map(SysWebAccRoleRelModel::getRoleId).collect(Collectors.toList());
        }
        //角色下绑定的资源权限和菜单
        List<Long> resourceIdList = Collections.emptyList();
        List<Long> menuIdList = Collections.emptyList();
        if (CollUtil.isNotEmpty(roleIdList)) {

            List<SysWebRoleModel> roleModels = sysWebRoleService.listByIds(roleIdList);
            if (CollUtil.isNotEmpty(roleModels)) {
                perRoleList = BeanUtil.copyToList(roleModels,PermissionDTO.Role.class);
            }

            List<SysWebRoleResRelModel> roleResRelModelList = sysWebRoleResRelService.list(
                    new LambdaQueryWrapper<SysWebRoleResRelModel>()
                            .select(SysWebRoleResRelModel::getResourceId)
                            .eq(SysWebRoleResRelModel::getDeleted, Constant.NOT_DELETE)
                            .in(SysWebRoleResRelModel::getRoleId, roleIdList)
            );
            if (CollUtil.isNotEmpty(roleResRelModelList)) {
                resourceIdList = roleResRelModelList.stream().map(SysWebRoleResRelModel::getResourceId).collect(Collectors.toList());
            }
            List<SysWebRoleMenuRelModel> roleMenuRelModelList = sysWebRoleMenuRelService.list(
                    new LambdaQueryWrapper<SysWebRoleMenuRelModel>()
                        .select(SysWebRoleMenuRelModel::getMenuId)
                        .eq(SysWebRoleMenuRelModel::getDeleted,Constant.NOT_DELETE)
                        .in(SysWebRoleMenuRelModel::getRoleId,roleIdList)
            );
            if (CollUtil.isNotEmpty(roleMenuRelModelList)) {
                menuIdList = roleMenuRelModelList.stream().map(SysWebRoleMenuRelModel::getMenuId).collect(Collectors.toList());
            }

        }
        //资源编码
        List<PermissionDTO.Resource> perResourceList = new ArrayList<>();
        if (CollUtil.isNotEmpty(resourceIdList)) {
            List<SysWebResourceModel> resourceModelList = sysWebResourceService.listByIds(resourceIdList);
            if (CollUtil.isNotEmpty(resourceModelList)) {
                perResourceList = BeanUtil.copyToList(resourceModelList,PermissionDTO.Resource.class);
            }
        }
        //菜单
        List<PermissionDTO.Menu> perMenuList = new ArrayList<>();
        if (CollUtil.isNotEmpty(menuIdList)) {
            List<SysWebMenuModel> menuModelList = sysWebMenuService.listByIds(menuIdList);
            if (CollUtil.isNotEmpty(menuModelList)) {
                perMenuList = BeanUtil.copyToList(menuModelList,PermissionDTO.Menu.class);
            }
        }
        return PermissionDTO.builder()
                .account(perAccount)
                .resources(perResourceList)
                .role(perRoleList)
                .menus(perMenuList)
                .build();
    }
}
