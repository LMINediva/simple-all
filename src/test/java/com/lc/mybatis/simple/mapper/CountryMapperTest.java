package com.lc.mybatis.simple.mapper;

import com.lc.mybatis.simple.model.Country;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryMapperTest extends BaseMapperTest {

    @Test
    public void testMapperWithStartPage3() {
        SqlSession sqlSession = getSqlSession();
        CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
        try {
            // 获取第1页，10条内容，默认查询总数count
            Map<String, Object> params = new HashMap<>();
            countryMapper.selectCountries(params);
            List<Country> list1 = (List<Country>) params.get("list1");
            List<Country> list2 = (List<Country>) params.get("list2");
            Assertions.assertNotNull(list1);
            Assertions.assertNotNull(list2);
        } finally {
            sqlSession.close();
        }
    }

}