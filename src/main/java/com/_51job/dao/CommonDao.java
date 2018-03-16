package com._51job.dao;

import com._51job.domain.Dictionary;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.domain.User;
import com._51job.tool.DataUtil;
import javafx.scene.input.DataFormat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import com._51job.tool.SerializeUtil;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        Query query=getSession().createQuery(hql);
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
        Query<Dictionary> query=getSession().createQuery("from Dictionary where dictionaryName like '%"+city+"%'", Dictionary.class);
        List<Dictionary> list=query.list();
        return list.get(0).getDictionaryId();
    }
    //根据String degree从dictionary搜索int degree
    public int getDegreeId(String degree){
        String hql= "from Dictionary where dictionaryName = :dictionaryName";
        Query <Dictionary> query=getSession().createQuery(hql);
        query.setParameter("dictionaryName",degree);
        List<Dictionary> list = query.list();
        return list.get(0).getDictionaryId();
    }

    //根据enterpriseId在数据库里查询该公司的信息
    public Enterprise searchEnterprise(int enterpriseId){
        return getSession().createQuery("from Enterprise where enterpriseId="+enterpriseId+"",Enterprise.class).list().get(0);

    }
    //根据dictionaryId在数据库里查其type
    public int searchType(int dictionaryId){
        String hql=" from Dictionary D where dictionaryId= :id";
        Query<Dictionary> query=getSession().createQuery(hql);
        query.setParameter("id",dictionaryId);
        List<Dictionary> list = query.list();
        return list.get(0).getType();

    }
    //获得实际的时间
    public String getActualTime(Timestamp time){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeStr = sdf.format(time);
        return timeStr;
    }

    public String getActualAttribute(int dicId,int type){
        List<Dictionary> dic=new ArrayList<>();
        String str=new String();
        switch (type){
            case 1:dic= DataUtil.allCities();break;
            case 2:dic=DataUtil.allIndystries();break;
            case 3:dic=DataUtil.allFuctions();break;
            case 4:dic=DataUtil.allEnterpriseType();break;
            case 5:dic=DataUtil.allSkills();break;
            case 6:dic=DataUtil.allLanguages();break;
            case 7:dic=DataUtil.allScales();break;
            case 9:dic=DataUtil.allDegrees();break;
        }
        for (int i = 0; i < dic.size(); i++) {
            if(dic.get(i).getDictionaryId()==dicId) str=dic.get(i).getDictionaryName();
        }
        // System.out.println("str is "+str);
        return str;
    }

}
