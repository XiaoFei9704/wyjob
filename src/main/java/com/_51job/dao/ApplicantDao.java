
package com._51job.dao;


import com._51job.domain.*;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ApplicantDao extends MyDao{

    @Autowired
    public ApplicantDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

//    //返回投递岗位及其状态
//    //将Application表和Recruitment表合并
//    public List<PostInfoState> post_state(List<Application> lst1, List<Recruitment> lst2, List<Enterprise> lst3, int lsize){
//        List<PostInfoState> lst = null;
//        PostInfoState postInfoState = new PostInfoState();
//        for(int i=0;i<lsize;i++){
//            postInfoState.setApplication(lst1.get(i));
//            postInfoState.setRecruitment(lst2.get(i));
//            postInfoState.setEnterprise(lst3.get(i));
//            lst.add(postInfoState);
//        }
//        return lst;
//  }
//  public List<EnterpriseResume> post_info(List<Recruitment> lst1,List<Enterprise> lst2,int lsize){
//      List<EnterpriseResume> lst = null;
//      EnterpriseResume enterpriseResume = new EnterpriseResume();
//      for (int i=0;i<lsize;i++){
//          enterpriseResume.setRecruitment(lst1.get(i));
//          enterpriseResume.setEnterprise(lst2.get(i));
//          lst.add(enterpriseResume);
//      }
//      return lst;
//  }

    public boolean toudi(int a_id, int r_id){
        Query<Application> query=getSession().createQuery("from Application where applicant_id="+a_id+" and recruitment_id="+r_id, Application.class);
        return query.list().size()>0;
    }

    public boolean hasUser(String username){
        Query<User> query=getSession().createQuery("from User where user_name='"+username+"'", User.class);
        return query.list().size()>0;
    }

    //获得某个求职者的经历信息
    public List<Experience> getExperienceList(int applicantId) {
        String hql = "from Experience where userId=:applicantId";
        Query<Experience> query = getSession().createQuery(hql, Experience.class);
        query.setParameter("applicantId", applicantId);
        List<Experience> list = query.list();
        if (!list.isEmpty()) return list;
        else return null;
    }

    //判断求职者某条经历是否是教育经历
    public boolean isEduExp(int expId) {
        String hql = "from EducationExperience where eexperienceId=:id";
        Query<EducationExperience> query = getSession().createQuery(hql, EducationExperience.class);
        query.setParameter("id", expId);
        List<EducationExperience> list = query.list();
        if (!list.isEmpty()) return true;
        else return false;
    }
    //判断求职者某条经历是否是工作经历
    public boolean isTrainExp(int expId) {
        String hql = "from TrainingExperience where texperienceId=:id";
        Query<TrainingExperience> query = getSession().createQuery(hql, TrainingExperience.class);
        query.setParameter("id", expId);
        List<TrainingExperience> list = query.list();
        if (!list.isEmpty()) return true;
        else return false;
    }
    //根据某个教育经历ID在数据库中找出该条教育经历记录
    public EducationExperience getEduExp(int expId){
        String hql = "from EducationExperience where eexperienceId=:id";
        Query<EducationExperience> query = getSession().createQuery(hql, EducationExperience.class);
        query.setParameter("id", expId);
        List<EducationExperience> list = query.list();
        if (!list.isEmpty()) return list.get(0);
        else return null;
    }
    /* //根据某个工作经历ID在数据库中找出该条工作经历记录
     public TrainingExperience getTrainExp(int expId){
         String hql = "from TrainingExperience where texperienceId=:id";
         Query<TrainingExperience> query = getSession().createQuery(hql, TrainingExperience.class);
         query.setParameter("id", expId);
         List<TrainingExperience> list = query.list();
         if (!list.isEmpty()) return list.get(0);
         else return null;
     }*/
    //从求职者经历中筛选出教育经历
    public List<EducationExperience> getEduList(List<Experience> expList) {
        List<EducationExperience> eduExpList = new ArrayList<>();
        for (Experience exp : expList) {
            if (isEduExp(exp.getExperienceId()))
                eduExpList.add(getEduExp(exp.getExperienceId()));
        }
        return eduExpList;
    }
    //从求职者经历中筛选出工作经历,并以经历类型返回（因为需要计算的是开始及结束时间~）
    public List<Experience> getTrainList(List<Experience> expList) {
        List<Experience> trainExpList = new ArrayList<>();
        for (Experience exp : expList) {
            if (isTrainExp(exp.getExperienceId()))
                trainExpList.add(exp);
        }
        return trainExpList;
    }
    /*//根据skillname从字典中找出该skill的实际名称
    public String getSkillName(int skill){
        String hql = "from Dictionary d where dictionaryId=:id";
        Query<Dictionary> query = getSession().createQuery(hql, Dictionary.class);
        query.setParameter("id", skill);
        List<Dictionary> list = query.list();
        if(!list.isEmpty()){
            return list.get(0).getDictionaryName();
        }
        else return null;
    }*/
    //获得某个求职者的所有技能
    public List<String> getSkillList(int applicantId){
        List<String> skillList=new ArrayList<>();
        String hql = "from Skill  where userId=:id";
        Query<Skill> query = getSession().createQuery(hql, Skill.class);
        query.setParameter("id", applicantId);
        List<Skill> list = query.list();
        for(Skill skill:list){
            skillList.add(skill.getActualSkillName());
        }
        return skillList;
    }
    //获得某个求职者的薪资要求
    public int getSalary(int applicantId){
        String hql = "from Applicant where userId=:id";
        Query<Applicant> query = getSession().createQuery(hql, Applicant.class);
        query.setParameter("id", applicantId);
        List <Applicant>list = query.list();
        if(!list.isEmpty()) return list.get(0).getSalaryLowerBound();
        else return 0;
    }
}

