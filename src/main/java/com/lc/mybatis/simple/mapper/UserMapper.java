package com.lc.mybatis.simple.mapper;

import com.lc.mybatis.simple.model.SysRole;
import com.lc.mybatis.simple.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author DELL
 * @date 2022/5/9 16:58
 */
public interface UserMapper {
    /**
     * 通过id查询用户
     *
     * @param id 用户id
     * @return 查询指定id的用户
     */
    SysUser selectById(Long id);

    /**
     * 查询全部用户
     *
     * @return 查询到的全部用户
     */
    List<SysUser> selectAll();

    /**
     * 根据用户id获取角色信息
     *
     * @param userId 用户id
     * @return 查询到的指定id用户的角色
     */
    List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 新增用户
     *
     * @param sysUser 要新增的用户
     * @return 新增用户结果
     */
    int insert(SysUser sysUser);

    /**
     * 新增用户-使用 useGeneratedKeys方式
     *
     * @param sysUser 新增的用户
     * @return 自增主键值
     */
    int insert2(SysUser sysUser);

    /**
     * 新增用户-使用selectKey方式
     *
     * @param sysUser 新增的用户
     * @return 自增主键值
     */
    int insert3(SysUser sysUser);

    /**
     * 根据主键更新
     *
     * @param sysUser 要更新的用户
     * @return 更新的用户数量
     */
    int updateById(SysUser sysUser);

    /**
     * 通过主键删除用户
     *
     * @param id 要删除的用户的主键
     * @return 删除用户的数量
     */
    int deleteById(Long id);

    /**
     * 通过主键删除
     *
     * @param sysUser 要删除的用户
     * @return 删除的用户数量
     */
    int deleteById(SysUser sysUser);

    /**
     * 根据用户id和角色的enabled状态获取用户的角色
     *
     * @param userId  用户ID
     * @param enabled 角色的启用状态
     * @return 符合上述条件的用户列表
     */
    List<SysRole> selectRolesByUserIdAndRoleEnabled(
            @Param("userId") Long userId,
            @Param("enabled") Integer enabled);

    /**
     * 根据用户id和角色的enabled状态获取用户的角色
     *
     * @param user 用户
     * @param role 角色
     * @return 符合上述条件的用户列表
     */
    List<SysRole> selectRolesByUserAndRole(
            @Param("user") SysUser user,
            @Param("role") SysRole role);

    /**
     * 根据用户id获取用户信息和用户的角色信息
     *
     * @param id
     * @return
     */
    SysUser selectUserAndRoleById(Long id);

    /**
     * 根据用户id获取用户信息和用户的角色信息
     *
     * @param id
     * @return
     */
    SysUser selectUserAndRoleById2(Long id);

    /**
     * 根据用户id获取用户信息和用户的角色信息，嵌套查询方式
     *
     * @param id
     * @return
     */
    SysUser selectUserAndRoleByIdSelect(Long id);

    /**
     * 获取所有的用户以及对应的所有角色
     *
     * @return
     */
    List<SysUser> selectAllUserAndRoles();

    /**
     * 通过嵌套查询获取指定用户的信息以及用户的角色和权限信息
     *
     * @param id
     * @return
     */
    SysUser selectAllUserAndRolesSelect(Long id);

    /**
     * 使用存储过程查询用户信息
     *
     * @param user
     */
    void selectUserById(SysUser user);

    /**
     * 使用存储过程分页查询
     *
     * @param params
     * @return
     */
    List<SysUser> selectUserPage(Map<String, Object> params);

    /**
     * 保存用户信息和角色关联信息
     *
     * @param user
     * @param roleIds
     * @return
     */
    int insertUserAndRoles(@Param("user") SysUser user, @Param("roleIds") String roleIds);

    /**
     * 根据用户id删除用户和用户的角色信息
     *
     * @param id
     * @return
     */
    int deleteUserById(Long id);

}