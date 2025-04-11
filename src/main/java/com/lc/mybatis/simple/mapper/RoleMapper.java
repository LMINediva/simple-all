package com.lc.mybatis.simple.mapper;

import com.lc.mybatis.simple.model.SysRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper {

    @Select({"select id, role_name roleName, enabled, create_by createBy, create_time createTime",
            "from sys_role",
            "where id = #{id}"})
    SysRole selectById(Long id);

    @Results(id = "roleResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("select id,role_name, enabled, create_by, create_time from sys_role where id = #{id}")
    SysRole selectById2(Long id);

    @ResultMap("roleResultMap")
    @Select("select * from sys_role")
    List<SysRole> selectAll();

    List<SysRole> selectAll(RowBounds rowBounds);

    @Update({"update sys_role",
            "set role_name = #{roleName},",
            "enabled = #{enabled},",
            "create_by = #{createBy},",
            "create_time = #{createTime,jdbcType=TIMESTAMP}",
            "where id = #{id}"})
    int updateById(SysRole sysRole);

    /**
     * 根据用户id获取用户的角色信息
     *
     * @param id
     * @return
     */
    SysRole selectRoleById(Long id);

    /**
     * 获取所有角色和对应的权限信息
     *
     * @return
     */
    List<SysRole> selectAllRoleAndPrivileges();

    /**
     * 根据用户id获取用户角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> selectRoleByUserId(Long userId);

    /**
     * 根据用户id获取用户的角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> selectRoleByUserIdChoose(Long userId);

}