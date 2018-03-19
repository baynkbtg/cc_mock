package com.mock.dao;

import com.mock.pojo.MockInfo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Created by qilong.chen on 2017/3/19.
 */
public interface MockInfoDao {
    @Select(value = "select * from mock_info")
    @Results(value = { @Result(id = true, property = "id", column = "id"), @Result(property = "alias", column = "alias"),
            @Result(property = "proto", column = "proto"), @Result(property = "status", column = "status"),
            @Result(property = "domain", column = "domain"), @Result(property = "url", column = "url"),
            @Result(property = "json", column = "json"), @Result(property = "fileName", column = "filename") })

    void insert(MockInfo mockInfo);

    void update(MockInfo mockInfo);

    void delete(int id);
}
