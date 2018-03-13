package com._51job.dao;

import com._51job.domain.Recruitment;
import com._51job.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDao extends MyDao{
    public CommonDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    //账号密码匹配
    public User getMatchCount(String username, String password){
        Query<User> query=getSession().createQuery("from User where userName="+username+" and password="+password, User.class);
        if(query.list().size()>0){
            return query.list().get(0);
        }
        else return null;
    }

    //账户查重（true:用户名可用；false:用户名重复）
    public boolean checkCount(String username){
        Query<User> query=getSession().createQuery("from User where userName="+username+"",User.class);
        if(query.list().size()>0)
            return false;
        else return true;
    }

    //获取岗位详情
    public Recruitment recruitment(int recruitment_id){
        Query<Recruitment> query=getSession().createQuery("from Recruitment where recruitmentId="+recruitment_id+"",Recruitment.class);
        return query.list().get(0);
    }

    //根据条件搜索enterprise_id
    public int  enterprise_id(int recruitment_id){
        int enterprise_id=getSession().createQuery("from Recruitment R where recruitmentId="+recruitment_id+"",Recruitment.class).list().get(0).getEnterpriseId();
        return enterprise_id;
    }

    //只有address
    //只有post/enterprisename
    //只有salary
    //只有minSeniority
    //只有minDegree
    //两个参数，address&post/enterprisename
    //两个参数





}
