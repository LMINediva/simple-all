package com.lc.mybatis.simple.mapper;

import com.lc.mybatis.simple.model.SysPrivilege;
import com.lc.mybatis.simple.model.SysRole;
import com.lc.mybatis.simple.plugin.PageRowBounds;
import com.lc.mybatis.simple.type.Enabled;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RoleMapperTest extends BaseMapperTest {

    @Test
    void selectAllRoleAndPrivileges() {
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取RoleMapper接口
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectAllRoleAndPrivileges();
            for (SysRole role : roleList) {
                System.out.println("角色名：" + role.getRoleName());
                for (SysPrivilege privilege : role.getPrivilegeList()) {
                    System.out.println("权限名：" + privilege.getPrivilegeName());
                }
            }
        } finally {
            // 不要忘记关闭SqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserIdChoose() {
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取RoleMapper接口
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            // 由于数据库数据enable都是1，所以给其中一个角色的enable赋值为0
            SysRole role = roleMapper.selectById(2L);
            role.setEnabled(Enabled.disabled);
            roleMapper.updateById(role);
            // 获取用户1的角色
            List<SysRole> roleList = roleMapper.selectRoleByUserIdChoose(1L);
            for (SysRole r : roleList) {
                System.out.println("角色名：" + r.getRoleName());
                if (r.getId().equals(1L)) {
                    // 第一个角色存在权限信息
                    Assertions.assertNotNull(r.getPrivilegeList());
                } else if (r.getId().equals(2L)) {
                    // 第二个角色的权限为null
                    Assertions.assertNull(r.getPrivilegeList());
                    continue;
                }
                for (SysPrivilege privilege : r.getPrivilegeList()) {
                    System.out.println("权限名：" + privilege.getPrivilegeName());
                }
            }
        } finally {
            // 不要忘记关闭SqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            // 先查询出角色，然后修改角色的enabled值为disabled
            SysRole role = roleMapper.selectById(2L);
            Assertions.assertEquals(Enabled.enabled, role.getEnabled());
            role.setEnabled(Enabled.disabled);
            roleMapper.updateById(role);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllByRowBounds() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            // 查询第一个，使用RowBounds类型时不会查询总数
            RowBounds rowBounds = new RowBounds(0, 1);
            List<SysRole> list = roleMapper.selectAll(rowBounds);
            for (SysRole role : list) {
                System.out.println("角色名：" + role.getRoleName());
            }
            // 使用PageRowBounds时会查询总数
            PageRowBounds pageRowBounds = new PageRowBounds(0, 1);
            list = roleMapper.selectAll(pageRowBounds);
            // 获取总数
            System.out.println("查询总数：" + pageRowBounds.getTotal());
            for (SysRole role : list) {
                System.out.println("角色名：" + role.getRoleName());
            }
            // 再次查询获取第二个角色
            pageRowBounds = new PageRowBounds(1, 1);
            list = roleMapper.selectAll(pageRowBounds);
            // 获取总数
            System.out.println("查询总数：" + pageRowBounds.getTotal());
            for (SysRole role : list) {
                System.out.println("角色名：" + role.getRoleName());
            }
        } finally {
            sqlSession.close();
        }
    }

}