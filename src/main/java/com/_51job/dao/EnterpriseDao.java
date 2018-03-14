package com._51job.dao;

import com._51job.domain.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.taglibs.standard.lang.jstl.NullLiteral;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.management.Query;
import java.util.List;

@Repository
public class EnterpriseDao extends MyDao {
    @Autowired
    public EnterpriseDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    //根据公司企业id获得他们发布的招聘信息
    public List<Recruitment> getRecruitmentList(int companyId){
        return getSession().createQuery("from Recruitment where enterpriseId="+companyId+"",Recruitment.class).list();
    }

    //根据某个招聘id获得所有应聘信息application列表
    public List<Application> getSpecificApplicationList(int recruitmentId){
        return getSession().createQuery("from Application where recruitmentId="+recruitmentId,Application.class).list();
    }

    //根据应聘信息id获取求职者id
    public int getApplicantId(int applicationId){
        return getSession().createQuery("from Application where applicationId="+applicationId, Application.class).list().get(0).getApplicantId();
    }

    //获得某个求职者的经历信息
    public List<Experience> getExperienceList(int applicantId){
        return getSession().createQuery("from Experience where userId="+applicantId,Experience.class).list();
    }

    //获得某个求职者的掌握的技能
    public List<Skill> getSkillList(int userId){
        return getSession().createQuery("from Skill where userId="+userId,Skill.class).list();
    }

    //获得某个求职者的掌握的技能
    public List<Language> getLanguageList(int userId){
        return getSession().createQuery("from Language where userId="+userId,Language.class).list();
    }

    //获得某个求职者的期望行业
    public List<PreferredIndustry> getExpectedIndusty(int userId){
        return getSession().createQuery("from PreferredIndustry where userId="+userId,PreferredIndustry.class).list();
    }

    //获得某个求职者的期望职能
    public List<PreferredFunction> getExpectedFunction(int userId){
        return getSession().createQuery("from PreferredFunction where userId="+userId,PreferredFunction.class).list();
    }

    //获得某个求职者的期望工作地点
    public List<PreferredLocation> getExpectedLocation(int userId){
        return getSession().createQuery("from PreferredLocation where userId="+userId,PreferredLocation.class).list();
    }
}
