package com.lc.mybatis.simple.mapper;

import com.lc.mybatis.simple.model.SysPrivilege;
import com.lc.mybatis.simple.model.SysRole;
import com.lc.mybatis.simple.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperTest extends BaseMapperTest {

    /**
     * 根据用户id获取用户信息和用户的角色信息
     */
    @Test
    void testSelectUserAndRoleById() {
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 特别注意，在测试数据中，id=1L的用户有两个角色，不适合这个例子
            // 这是使用只有一个角色的用户（id=1001L）
            // 可以用 selectUserAndRoleById2 替换进行测试
            SysUser user = userMapper.selectUserAndRoleById2(1001L);
            // user不为空
            Assertions.assertNotNull(user);
            // user.role也不为空
            Assertions.assertNotNull(user.getRole());
        } finally {
            // 不要忘记关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserAndRoleByIdSelect() {
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 这里使用只有一个角色的用户（id = 1001L）
            SysUser user = userMapper.selectUserAndRoleByIdSelect(1001L);
            // user不为空
            Assertions.assertNotNull(user);
            // user.role也不为空
            System.out.println("调用user.equals(null)");
            user.equals(null);
            System.out.println("调用user.getRole()");
            Assertions.assertNotNull(user.getRole());
        } finally {
            // 不要忘记关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllUserAndRoles() {
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAllUserAndRoles();
            System.out.println("用户数：" + userList.size());
            for (SysUser user : userList) {
                System.out.println("用户名：" + user.getUserName());
                for (SysRole role : user.getRoleList()) {
                    System.out.println("角色名：" + role.getRoleName());
                    for (SysPrivilege privilege : role.getPrivilegeList()) {
                        System.out.println("权限名：" + privilege.getPrivilegeName());
                    }
                }
            }
        } finally {
            // 不要忘记关闭SqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllUserAndRolesSelect() {
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectAllUserAndRolesSelect(1L);
            System.out.println("用户名：" + user.getUserName());
            for (SysRole role : user.getRoleList()) {
                System.out.println("角色名：" + role.getRoleName());
                for (SysPrivilege privilege : role.getPrivilegeList()) {
                    System.out.println("权限名：" + privilege.getPrivilegeName());
                }
            }
        } finally {
            // 不要忘记关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserById() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setId(1L);
            userMapper.selectUserById(user);
            Assertions.assertNotNull(user.getUserName());
            System.out.println("用户名：" + user.getUserName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserPage() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> params = new HashMap<>();
            params.put("userName", "ad");
            params.put("offset", 0);
            params.put("limit", 10);
            List<SysUser> userList = userMapper.selectUserPage(params);
            Long total = (Long) params.get("total");
            System.out.println("总数：" + total);
            for (SysUser user : userList) {
                System.out.println("用户名：" + user.getUserName());
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertAndDelete() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.tk");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1, 2, 3});
            // 插入用户信息和角色关联信息
            userMapper.insertUserAndRoles(user, "1,2");
            Assertions.assertNotNull(user.getId());
            Assertions.assertNotNull(user.getCreateTime());
            // 可以执行下面的commit后再查看数据库钟的数据
            // sqlSession.commit();
            // 测试删除刚刚插入的数据
            userMapper.deleteUserById(user.getId());
        } finally {
            sqlSession.close();
        }
    }

}