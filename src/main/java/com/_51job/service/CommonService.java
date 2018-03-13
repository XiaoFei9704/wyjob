package com._51job.service;

import com._51job.dao.CommonDao;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
    private final CommonDao commonDao;

    @Autowired
    public CommonService(CommonDao commonDao) {
        this.commonDao = commonDao;
    }
    //登陆
    public User signin(String username,String password){
        User user=commonDao.getMatchCount(username, password);
        return user;
    }
    //账户查重（true:用户名可用；false:用户名重复）
    public boolean checkAccount(String username){
        return commonDao.checkCount(username);
    }
    //获取用户信息（没有登陆则返回null）
    public Enterprise userInfo(int user_id){

        Enterprise userInfo=commonDao.get(Enterprise.class,user_id);
        return userInfo;
    }

    //获取岗位详情
    public Recruitment job(int recuitment_id){
        Recruitment job=commonDao.get(Recruitment.class,recuitment_id);
        return job;
    }

}


