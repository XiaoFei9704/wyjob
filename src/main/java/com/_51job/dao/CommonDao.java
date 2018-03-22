package com._51job.dao;

import com._51job.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommonDao extends MyDao{
    @Autowired
    public CommonDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    //账号密码匹配
    public User getMatchCount(String username, String password){
        String hql="from User where userName=:name and password=:password";
        Query<User> query=getSession().createQuery(hql,User.class);
        query.setParameter("name", username);
        query.setParameter("password", password);
        List <User>list = query.list();
        if(!list.isEmpty()){
            return list.get(0);
        }
        else return null;
    }

    //账户查重（true:用户名可用；false:用户名重复）
    public boolean checkCount(String username){
        String hql="from User where userName=:name";
        Query query=getSession().createQuery(hql);
        query.setParameter("name", username);
        List<User> list=query.list();
        if(!list.isEmpty())
            return false;
        else return true;
    }



}