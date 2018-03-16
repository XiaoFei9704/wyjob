package com._51job.dao;

import com._51job.domain.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
    public List<PreferredIndustry> getExpectedIndustyList(int userId){
        return getSession().createQuery("from PreferredIndustry where userId="+userId,PreferredIndustry.class).list();
    }

    //获得某个求职者的期望职能
    public List<PreferredFunction> getExpectedFunctionList(int userId){
        return getSession().createQuery("from PreferredFunction where userId="+userId,PreferredFunction.class).list();
    }

    //获得某个求职者的期望工作地点
    public List<PreferredLocation> getExpectedLocationList(int userId){
        return getSession().createQuery("from PreferredLocation where userId="+userId,PreferredLocation.class).list();
    }

    //根据账号和密码获取userID
    public int getUserId(String account,String password){
        User user=getSession().createQuery("from User where userName='"+account+"' and password='"+password+"'",User.class).list().get(0);
        return user.getUserId();
    }

    //判断教育经历是否有相应id记录
    public boolean judgeEducation(int id){
        boolean flag=false;
        List<EducationExperience> edu=getSession().createQuery("from EducationExperience where eexperienceId="+id,EducationExperience.class).list();
        if(!edu.isEmpty()) flag=true;
        return flag;
    }

    //判断工作经历是否有相应id记录
    public boolean judgeWork(int id){
        boolean flag=false;
        List<WorkingExperience> work=getSession().createQuery("from WorkingExperience where wexperienceId="+id,WorkingExperience.class).list();
        if(!work.isEmpty()) flag=true;
        return flag;
    }
}
