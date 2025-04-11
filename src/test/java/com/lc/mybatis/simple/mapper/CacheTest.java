package com.lc.mybatis.simple.mapper;

import com.lc.mybatis.simple.model.SysRole;
import com.lc.mybatis.simple.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CacheTest extends BaseMapperTest {

    @Test
    public void testL1Cache() {
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        SysUser user1 = null;
        try {
            // 获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 调用selectById方法，查询 id = 1 的用户
            user1 = userMapper.selectById(1L);
            // 对当前获取的对象重新赋值
            user1.setUserName("New Name");
            // 再次查询获取id相同的用户
            SysUser user2 = userMapper.selectById(1L);
            // 虽然没有更新数据库，但是这个用户名和user1重新赋值的名字相同
            Assertions.assertEquals("New Name", user2.getUserName());
            // 无论如何，user2和user1完全就是一个实例
            Assertions.assertEquals(user1, user2);
        } finally {
            // 关闭当前的SqlSession
            sqlSession.close();
        }

        System.out.println("开启新的SqlSession");
        // 开启另一个新的Session
        sqlSession = getSqlSession();
        try {
            // 获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 调用selectById方法，查询 id = 1 的用户
            SysUser user2 = userMapper.selectById(1L);
            // 第二个session获取的用户名仍然是admin
            Assertions.assertNotEquals("New Name", user2.getUserName());
            // 这里的user2和前一个session查询的结果是两个不同的实例
            Assertions.assertNotEquals(user1, user2);
            // 执行删除操作
            userMapper.deleteById(2L);
            // 获取user3
            SysUser user3 = userMapper.selectById(1L);
            // 这里的user2和user3是两个不同的实例
            Assertions.assertNotEquals(user2, user3);
        } finally {
            // 关闭SqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testL2Cache() {
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        SysRole role1 = null;
        try {
            // 获取RoleMapper接口
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            // 调用selectById方法，查询 id = 1 的用户
            role1 = roleMapper.selectById(1L);
            // 对当前获取的对象重新赋值
            role1.setRoleName("New Name");
            // 再次查询获取id相同的用户
            SysRole role2 = roleMapper.selectById(1L);
            // 虽然没有更新数据库，但是这个用户名和role1重新赋值的名字相同
            Assertions.assertEquals("New Name", role2.getRoleName());
            // 无论如何，role2和role1完全就是同一个实例
            Assertions.assertEquals(role1, role2);
        } finally {
            // 关闭当前的SqlSession
            sqlSession.close();
        }

        System.out.println("开启新的SqlSession");
        // 开始另一个新的session
        sqlSession = getSqlSession();
        try {
            // 获取RoleMapper接口
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            // 调用selectById方法，查询 id = 1 的用户
            SysRole role2 = roleMapper.selectById(1L);
            // 调用第二个session获取的用户名是New Name
            Assertions.assertEquals("New Name", role2.getRoleName());
            // 这里的role2和前一个session查询的结果是两个不同的实例
            Assertions.assertNotEquals(role1, role2);
            // 获取role3
            SysRole role3 = roleMapper.selectById(1L);
            // 这里的role2和role3是两个不同的实例
            Assertions.assertNotEquals(role2, role3);
        } finally {
            // 关闭SqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testDirtyData() {
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            Assertions.assertEquals("普通用户", user.getRole().getRoleName());
            System.out.println("角色名：" + user.getRole().getRoleName());
        } finally {
            sqlSession.close();
        }

        // 开始另一个新的session
        sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(2L);
            role.setRoleName("脏数据");
            roleMapper.updateById(role);
            // 提交修改
            sqlSession.commit();
        } finally {
            // 关闭当前的sqlSession
            sqlSession.close();
        }
        System.out.println("开启新的SqlSession");

        // 开始另一个新的session
        sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            SysRole role = roleMapper.selectById(2L);
            Assertions.assertEquals("普通用户", user.getRole().getRoleName());
            Assertions.assertEquals("脏数据", role.getRoleName());
            System.out.println("角色名：" + user.getRole().getRoleName());
            // 还原数据
            role.setRoleName("普通用户");
            roleMapper.updateById(role);
            // 提交修改
            sqlSession.commit();
        } finally {
            // 关闭SqlSession
            sqlSession.close();
        }

    }

}