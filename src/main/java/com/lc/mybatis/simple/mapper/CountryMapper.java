package com.lc.mybatis.simple.mapper;

import com.lc.mybatis.simple.model.Country;

import java.util.List;
import java.util.Map;

/**
 * @author DELL
 * @date 2022/5/9 16:54
 */
public interface CountryMapper {

    /**
     * 查询所有国家
     *
     * @return
     */
    List<Country> selectAll();

    /**
     * 执行Oracle中的存储过程
     *
     * @param params
     * @return
     */
    Object selectCountries(Map<String, Object> params);

}
