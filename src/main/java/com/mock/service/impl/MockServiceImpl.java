package com.mock.service.impl;

import com.mock.dao.MockInfoDao;
import com.mock.pojo.MockInfo;
import com.mock.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qilong.chen on 2018/3/20.
 */
@Service
public class MockServiceImpl implements MockService {

    @Autowired
    private MockInfoDao mockInfoDao;

    @Override
    public List<MockInfo> query(){
        return this.mockInfoDao.query();
    }

    @Override
    public String queryByPath(String path) {
        return this.mockInfoDao.queryByPath(path);
    }

    @Override
    public void insert(MockInfo mockInfo) {
        this.mockInfoDao.insert(mockInfo);
    }

    @Override
    public void update(MockInfo mockInfo){
        this.mockInfoDao.update(mockInfo);
    }

    @Override
    public void delete(int id){
        this.mockInfoDao.delete(id);
    }
}