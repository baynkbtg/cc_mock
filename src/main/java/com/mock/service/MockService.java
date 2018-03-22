package com.mock.service;

import com.mock.pojo.MockInfo;

import java.util.List;

/**
 * Created by qilong.chen on 2018/3/20.
 */
public interface MockService {

    List<MockInfo> query();

    String queryByPath(String path);

    void insert(MockInfo mockInfo);

    void update(MockInfo mockInfo);

    void delete(int id);
}
