package com._51job.service;

import com._51job.dao.EnterpriseDao;
import com._51job.domain.*;
import com._51job.web.PostInfo;
import com._51job.web.ResumeInfo;
import com._51job.web.SimpleResume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnterpriseService {

    private final EnterpriseDao enterpriseDao;

    @Autowired
    public EnterpriseService(EnterpriseDao enterpriseDao) {
        this.enterpriseDao = enterpriseDao;
    }

    //获取所发布的岗位列表以及状态
    public List<PostInfo> getPost(int companyId){
        PostInfo post=new PostInfo();
        Enterprise en=enterpriseDao.get(Enterprise.class,companyId);
        //获得实际的地址、规模、行业
        String address=getActualAttribute1(en.getDomicile());
        String scale=getActualAttribute1(en.getScale());
        String industry=getActualAttribute2(en.getIndustry());
        en.setActualDomicile(address);
        en.setActualScale(scale);
        en.setActualIndustry(new StringBuffer(industry));
        //获得企业发布的招聘信息
        List<Recruitment> list=enterpriseDao.getRecruitmentList(companyId);
        List<PostInfo> list1 = null;
        for (int i = 0; i < list.size(); i++) {
            post.setEnterprise(en);
            //获得实际的岗位开放状态和时间
            String st=getRecruitState(list.get(i).getState());
            list.get(i).setActualState(st);
            String time=getRecruitTime(list.get(i).getTime());
            list.get(i).setActualTime(time);
            post.setRecruitment(list.get(i));
            list1.add(i,post);
        }
        return list1;
    }

    //获取指定岗位投递来的简历列表
    public List<SimpleResume> getSpecificPost(int recruitmentId){
        SimpleResume simpleResume=new SimpleResume();
        List<SimpleResume> simpleList=null;
        List<Application> applicationList=enterpriseDao.getSpecificApplicationList(recruitmentId);
        Applicant applicant=new Applicant();
        for (int i = 0; i < applicationList.size(); i++) {
            int applicantId=applicationList.get(i).getApplicantId();
            applicant=enterpriseDao.get(Applicant.class,applicantId);
            simpleResume.setName(applicant.getName());
            simpleResume.setGender(getPersonGender(applicant.getGender()));
            simpleResume.setWorkStatus(getCurrentWorkStatus(applicant.getWorkingStatus()));
            simpleResume.setApplicationTime(getActualTime(applicationList.get(i).getApplicationTime()));
            simpleResume.setApplicationState(getActualWorkState(applicationList.get(i).getApplicationState()));
            String degree=getHighestDegree(applicantId);
            simpleResume.setDegree(degree);
            simpleList.add(simpleResume);
        }
        return simpleList;
    }


    //获取指定的简历的详情
    public ResumeInfo getSpecificResume(int applicationId){
        ResumeInfo resumeInfo=new ResumeInfo();
        int applicantId=enterpriseDao.getApplicantId(applicationId);
        Applicant applicant=getActualInfoApplicant(applicantId);
        List<Experience> expList=getSpecificExperience(applicantId);
        List<EducationExperience> eduList=getActualStudyExperience(expList);
        List<WorkingExperience> workList=getActualWorkExperience(expList);
        List<Skill> skillList=getActualSkill(applicantId);
        List<Language> languagesList=getActualLanguage(applicantId);
        List<PreferredFunction> functionList=getExpectedFunction(applicantId);
        List<PreferredIndustry> industryList=getExpectedIndustry(applicantId);
        List<PreferredLocation> locationList=getExpectedLocation(applicantId);
        resumeInfo.setApplicant(applicant);
        resumeInfo.setExperienceList(expList);
        resumeInfo.setEducationList(eduList);
        resumeInfo.setWorkingList(workList);
        resumeInfo.setSkillList(skillList);
        resumeInfo.setLanguageList(languagesList);
        resumeInfo.setPreFunctionList(functionList);
        resumeInfo.setPreIndustryList(industryList);
        resumeInfo.setPreLocationList(locationList);
        return new ResumeInfo();
    }

    //改变投递的简历的状态
    public int changeRecruitState(int state,int resumeId){
        //Application app=enterpriseDao.getSession().createQuery("from Application where applicationId="+resumeId+"",Application.class).list().get(0);
        Application app=enterpriseDao.get(Application.class,resumeId);
        app.setApplicationState(state);
        enterpriseDao.update(app);
        return app.getApplicationState();
    }


    //关闭招聘信息
    public boolean closeRecruit(int jobId){
        System.out.println("进入关闭函数");
        Recruitment ru=enterpriseDao.get(Recruitment.class,jobId);
        System.out.println("获得招聘信息ru"+ru.getRecruitmentId());
        System.out.println(ru.getPost());
        Byte st=2;
        System.out.println("准备set招聘信息的状态,现在为"+ru.getPost());
        ru.setState(st);
        enterpriseDao.update(ru);
        System.out.println("设置完成");
        System.out.println(ru.getPost());
        System.out.println("更新完成"+ru.getState());
        if(ru.getState()==2) return true;
        else return false;
    }

    //注册公司
    public User getCompanyReg(String account,String password,String name){
        User user=new User();
        user.setUserName(account);
        user.setPassword(password);
        user.setRole(2);
        enterpriseDao.save(user);
        int user_id=enterpriseDao.getUserId(account,password);
        User user1=enterpriseDao.get(User.class,user_id);
        if(user1!=null){
            Enterprise enterprise=new Enterprise();
            enterprise.setEnterpriseId(user_id);
            enterprise.setName(name);
            enterpriseDao.save(enterprise);
        }
        return user1;
    }

    //获得某个求职者的最高学历
    public String getHighestDegree(int applicantId){
        List<Experience> exList=enterpriseDao.getExperienceList(applicantId);
        List<EducationExperience> eduList=getActualStudyExperience(exList);
        int highDegree=0;
        for (int i = 0; i < eduList.size(); i++) {
            int temp=eduList.get(i).getDegree();
            if(temp > highDegree) highDegree=temp;
        }
        return getActualAttribute1(highDegree);
    }

    //获得包括实际信息的applicant类
    public Applicant getActualInfoApplicant(int applicantId){
        Applicant applicant=enterpriseDao.get(Applicant.class,applicantId);
        applicant.setActualGender(getPersonGender(applicant.getGender()));
        applicant.setActualBirthdate(getActualTime(applicant.getBirthdate()));
        applicant.setActualDomicile(getActualAttribute1(applicant.getDomicile()));
        applicant.setActualWorkingStatus(getCurrentWorkStatus(applicant.getWorkingStatus()));
        applicant.setActualWorkType(getExpectedWorkType(applicant.getWorkType()));
        return applicant;
    }

    //获得某个求职者的工作经历列表
    public List<Experience> getSpecificExperience(int applicantId){
        List<Experience> list=enterpriseDao.getExperienceList(applicantId);
        for (int i = 0; i < list.size(); i++) {
            String start=getActualTime(list.get(i).getStartTime());
            String end=getActualTime(list.get(i).getEndTime());
            list.get(i).setActualStartTime(start);
            list.get(i).setActualEndTime(end);
        }
        return list;
    }

    //查找某个求职者的学习经历
    public List<EducationExperience> getActualStudyExperience(List<Experience> expList){
        List<EducationExperience> eduList = null;
        EducationExperience edu;
        for(int i=0;i<expList.size();i++){
            int id=expList.get(i).getExperienceId();
            edu=enterpriseDao.get(EducationExperience.class,id);
            if(edu!=null) {
                edu.setStartTime(expList.get(i).getActualStartTime());
                edu.setEndTime(expList.get(i).getActualEndTime());
                edu.setActualDegree(getActualAttribute1(edu.getDegree()));
                eduList.add(edu);
            }
        }
        return eduList;
    }


    //查找某个求职者的工作经历
    public List<WorkingExperience> getActualWorkExperience(List<Experience> expList){
        List<WorkingExperience> workList=null;
        WorkingExperience work;
        for(int i=0;i<expList.size();i++){
            int id=expList.get(i).getExperienceId();
            work=enterpriseDao.get(WorkingExperience.class,id);
            if(work!=null) {
                work.setStartTime(expList.get(i).getActualStartTime());
                work.setEndTime(expList.get(i).getActualEndTime());
                work.setActualEnterpriseType(getActualAttribute1(work.getEnterpriseType()));
                work.setActualEnterpriseScale(getActualAttribute1(work.getEnterpriseScale()));
                work.setActualFunction(getActualAttribute2(work.getFunction()));
                work.setActualIndustry(getActualAttribute2(work.getIndustry()));
                work.setActualWorkType(getActualAttribute1(work.getWorkType()));
                workList.add(work);
            }
        }
        return workList;
    }

    //获得某个求职者的掌握的技能
    public List<Skill> getActualSkill(int userId){
        List<Skill> list=enterpriseDao.getSkillList(userId);
        for (int i = 0; i < list.size(); i++) {
            Byte level=list.get(i).getLevel();
            int skillId=list.get(i).getSkillName();
            list.get(i).setActualSkillName(getActualAttribute1(skillId));
            list.get(i).setActualLevel(getActualSkillLevel(level));
        }
        return list;
    }

    //获得某个求职者的语言能力
    public List<Language> getActualLanguage(int userId){
        List<Language> list=enterpriseDao.getLanguageList(userId);
        for (int i = 0; i < list.size(); i++) {
            Byte rw=list.get(i).getRwAbility();
            Byte x=list.get(i).getxAbility();
            int language=list.get(i).getLanguage();
            list.get(i).setActualLanguage(getActualAttribute1(language));
            list.get(i).setActualRwAbility(getActualSkillLevel(rw));
            list.get(i).setActualXAbility(getActualSkillLevel(x));
        }
        return list;
    }

    //获取某个求职者的期望行业
    public List<PreferredIndustry> getExpectedIndustry(int userId){
        List<PreferredIndustry> list=getExpectedIndustry(userId);
        for (int i = 0; i < list.size(); i++) {
            int id=list.get(i).getIndustry();
            list.get(i).setActualIndustry(getActualAttribute2(id));
        }
        return list;
    }

    //获取某个求职者的期望职能
    public List<PreferredFunction> getExpectedFunction(int userId){
        List<PreferredFunction> list=getExpectedFunction(userId);
        for (int i = 0; i < list.size(); i++) {
            int id=list.get(i).getFunction();
            list.get(i).setActualFunction(getActualAttribute2(id));
        }
        return list;
    }

    //获取某个求职者的期望地点
    public List<PreferredLocation> getExpectedLocation(int userId){
        List<PreferredLocation> list=getExpectedLocation(userId);
        for (int i = 0; i < list.size(); i++) {
            int id=list.get(i).getLoactionId();
            list.get(i).setActualLocation(getActualAttribute2(id));
        }
        return list;
    }

    //获得实际地址、规模、企业类型、技能名称、语种、学历、角色
    public String getActualAttribute1(int dicId){
        String str="";
        Integer id=new Integer(dicId);
        while(id != null){
            Dictionary dic=enterpriseDao.get(Dictionary.class,dicId);
            str=dic.getDictionaryName()+str;
            id=Integer.valueOf(dic.getParent());
        }
        return str;
    }

    //获得实际行业、职能
    public String getActualAttribute2(int dicId){
        Dictionary dic=enterpriseDao.get(Dictionary.class,dicId);
        String str=dic.getDictionaryName();
        Integer id=Integer.valueOf(dic.getParent());
        while(id != null){
            dic=enterpriseDao.get(Dictionary.class,dicId);
            str=dic.getDictionaryName()+"--"+str;
            id=Integer.valueOf(dic.getParent());
        }
        return str;
    }

    //获得实际的岗位开放状态
    public String getRecruitState(int i){
        String state;
        if (i==1) state="开放";
        else state="关闭";
        return state;
    }

    //获得实际的岗位发布时间
    public String getRecruitTime(Timestamp time){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sdf.format(time);
        return timeStr;
    }

    //获得实际的时间
    public String getActualTime(Timestamp time){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeStr = sdf.format(time);
        return timeStr;
    }

    //获得经验的时间区段
    public String getExperienceTime(Timestamp start,Timestamp end){
        String et=getActualTime(start)+getActualTime(end);
        return et;
    }

    //获得实际的性别
    public String getPersonGender(byte i){
        String gender;
        if (i==1) gender="男";
        else gender="女";
        return gender;
    }

    //获得当前工作状态
    public String getCurrentWorkStatus(int state){
        String status;
        switch (state){
            case 1: status="离岗"; break;
            case 2: status="在岗"; break;
            default: status="在岗 & 有换岗需求";
        }
        return status;
    }

    //获得实际期望的工作类型
    public String getExpectedWorkType(int wt){
        String ewt;
        switch (wt){
            case 1: ewt="全职"; break;
            case 2: ewt="兼职"; break;
            default: ewt="实习";
        }
        return ewt;
    }

    //获得审核状态
    public String getActualWorkState(int ws){
        String aws;
        switch (ws){
            case 1: aws="审核中"; break;
            case 2: aws="已录用"; break;
            default: aws="未录用";
        }
        return aws;
    }

    //获得技能的实际掌握程度
    public String getActualSkillLevel(Byte sl){
        String asl;
        switch (sl){
            case 1: asl="一般"; break;
            case 2: asl="良好"; break;
            case 3:asl="熟练";break;
            default: asl="精通";
        }
        return asl;
    }
}
