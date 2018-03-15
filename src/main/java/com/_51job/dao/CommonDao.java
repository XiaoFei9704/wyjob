package com._51job.dao;

import com._51job.domain.Dictionary;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import com._51job.tool.SerializeUtil;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public class CommonDao extends MyDao{
    @Autowired
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
    public Recruitment searchRecruitment(int recruitment_id){
        Query<Recruitment> query=getSession().createQuery("from Recruitment where recruitmentId="+recruitment_id+"",Recruitment.class);
        return query.list().get(0);
    }

    //根据recruitmentId搜索enterprise_id
    public int  enterprise_id(int recruitment_id){
        return getSession().createQuery("from Recruitment R where recruitmentId="+recruitment_id+"",Recruitment.class).list().get(0).getEnterpriseId();

    }
    //根据String city从dictionary搜索int city
    public int getCityId(String city){
        return getSession().createQuery("from Dictionary where dictionaryName like '%"+city+"%'", Dictionary.class).list().get(0).getDictionaryId();
    }
    //根据String degree从dictionary搜索int degree
    public int getDegreeId(String degree){
        return getSession().createQuery("from Dictionary where dictionaryName = "+degree+"", Dictionary.class).list().get(0).getDictionaryId();
    }
    //根据enterpriseId在数据库里查询该公司的信息
    public Enterprise searchEnterprise(int enterpriseId){
        return getSession().createQuery("from Enterprise where enterpriseId="+enterpriseId+"",Enterprise.class).list().get(0);

    }
}
