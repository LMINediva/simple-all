package com.lc.mybatis.simple.mapper;

import com.lc.mybatis.simple.model.SysPrivilege;

import java.util.List;

public interface PrivilegeMapper {

    /**
     * 通过角色id获取该角色对应的所有权限信息
     *
     * @param roleId
     * @return
     */
    List<SysPrivilege> selectPrivilegeByRoleId(Long roleId);

}