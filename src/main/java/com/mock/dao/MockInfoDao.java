package com.mock.dao;

import com.mock.pojo.MockInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by qilong.chen on 2017/3/19.
 */
public interface MockInfoDao {
    @Select(value = "select * from mock_info")
    @Results(value = { @Result(id = true, property = "id", column = "id"), @Result(property = "alias", column = "alias"),
            @Result(property = "proto", column = "proto"), @Result(property = "domain", column = "domain"),
            @Result(property = "path", column = "path"), @Result(property = "json", column = "json") })

    List<MockInfo> query();

    String queryByPath(@Param("path") String path);

    void insert(MockInfo mockInfo);

    void update(MockInfo mockInfo);

    void delete(int id);
}
