package com._51job.service;


import com._51job.dao.ApplicantDao;
import com._51job.domain.*;
import com._51job.tool.DataUtil;
import com._51job.web.EnterpriseResume;
import com._51job.web.PostInfo;
import com._51job.web.PostInfoState;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class ApplicantService {

    @Autowired
    private ApplicantDao applicantDao;
    //ok
    //求职者注册
    public Applicant register(String account, String password) {
        User user = new User();
        user.setUserName(account);
        user.setPassword(password);
        user.setRole(1);
        int id = applicantDao.save(user);
        System.out.println(id);
        user.setUserId(id);
        Applicant applicant = new Applicant();
        applicant.setUserId(id);
        int user_id = applicantDao.save(applicant);
        if (user_id > 0) return applicant;
        return null;

    }

    //列出合适的岗位
    public List<EnterpriseResume> suitJobs(int userid) {
        List<EnterpriseResume> list=new ArrayList<>();
        Random random=new Random();
        if(userid==0){//没有登录，随机推荐20个
            for(int i=0;i<20;i++){
                Recruitment recruitment=DataUtil.getRecruitment(random.nextInt(90000));
                while (recruitment==null){
                    recruitment=DataUtil.getRecruitment(random.nextInt(90000));
                    if(recruitment!=null)break;
                }
                list.add(new EnterpriseResume(DataUtil.getEnterprise(recruitment.getEnterpriseId()),recruitment));
            }
        }else {
            List<Recruitment> recruitments=DataUtil.recommends(userid);
            for(Recruitment recruitment: recruitments){
                list.add(new EnterpriseResume(DataUtil.getEnterprise(recruitment.getEnterpriseId()),recruitment));
            }
        }
        for(EnterpriseResume enterpriseResume: list){
            enterpriseResume.getEnterprise().setActualDomicile(DataUtil.getDictionary(enterpriseResume.getEnterprise().getDomicile()).getDictionaryName());
            enterpriseResume.getEnterprise().setActualScale(DataUtil.getDictionary(enterpriseResume.getEnterprise().getScale()).getDictionaryName());
            enterpriseResume.getEnterprise().setActualIndustry(DataUtil.getDictionary(enterpriseResume.getEnterprise().getIndustry()).getDictionaryName());
            enterpriseResume.getEnterprise().setActualType(DataUtil.getDictionary(enterpriseResume.getEnterprise().getType()).getDictionaryName());
            enterpriseResume.getRecruitment().setActualFunction(DataUtil.getDictionary(enterpriseResume.getRecruitment().getFunction()).getDictionaryName());
            enterpriseResume.getRecruitment().setActualMinDegree(DataUtil.getDictionary(enterpriseResume.getRecruitment().getMinDegree()).getDictionaryName());
        }
        return list;
    }
    //ok
    //获取投递的岗位以及对应的状态
    public List<PostInfoState> sentJobstate(int id){
        List<Application> list = applicantDao.oneToMany(id, Applicant.class, Application.class);
        List<PostInfoState> lst = new ArrayList<>();
        for (Application aList : list) {
            PostInfoState postInfoState = new PostInfoState();
            postInfoState.setApplication(aList);
            int recruitment_id = aList.getRecruitmentId();
            Recruitment recruitment = DataUtil.getRecruitment(recruitment_id);
            recruitment.setActualMinDegree(DataUtil.getDictionary(recruitment.getMinDegree()).getDictionaryName());
            postInfoState.setRecruitment(recruitment);
            Enterprise enterprise = DataUtil.getEnterprise(recruitment_id);
            enterprise.setActualDomicile(DataUtil.getDictionary(enterprise.getDomicile()).getDictionaryName());
            int scale = enterprise.getScale();
            enterprise.setActualScale(DataUtil.getDictionary(scale).getDictionaryName());
            int industry = enterprise.getIndustry();
            enterprise.setActualIndustry(DataUtil.getDictionary(industry).getDictionaryName());
            postInfoState.setEnterprise(enterprise);
            lst.add(postInfoState);
            switch (aList.getApplicationState()) {
                case 1:
                    aList.setActualApplicationState("审核中");
                    break;
                case 2:
                    aList.setActualApplicationState("已录用");
                    break;
                case 3:
                    aList.setActualApplicationState("未录用");
                    break;
            }
        }
        return lst;
    }


    //保存求职者简历
    public boolean saveResume(int user_id,String str_applicant,String str_language,
                String str_skill, String str_workexp,
                String str_eduexp) throws ParseException {

        boolean r1 = saveApplicant(str_applicant);
        boolean r2 = saveLanguage(str_language);
        boolean r3 =saveSkill(str_skill);
        boolean r4 =saveWorkingExperience(user_id,str_workexp);
        boolean r5 =saveEducationExperience(user_id,str_eduexp);
        if (r1&&r2&&r3&&r4&&r5)return true;
        return false;
    }

    public boolean applicate(int rId, int userId){
        Application application=new Application();
        application.setRecruitmentId(rId);
        application.setApplicantId(userId);
        application.setApplicationState(1);
        application.setApplicationTime(new Timestamp(new Date().getTime()));
        return applicantDao.save(application)>0;
    }

    //获取简历信息


    private boolean saveApplicant(String str_applicant){
        Applicant applicant = (Applicant) jsonTojava(str_applicant,Applicant.class);
        applicant.setGender(getBytegender(applicant.getActualGender()));
        applicant.setDomicile(getIntDomicile(applicant.getActualDomicile()));
        applicant.setWorkingStatus(getByteWorkStatus(applicant.getActualWorkingStatus()));
        applicant.setWorkType(getByteWorkType(applicant.getActualWorkType()));
        int applicant_id = applicantDao.save(applicant);
        applicant.setUserId(applicant_id);
        if (applicant_id  > 0) return true;
        return false;
    }

    public boolean saveLanguage(String str_language){
        List<Language> languageList = JSON.parseArray(str_language,Language.class);
        for (Language language:languageList){
            language.setLanguage(getIntLanguage(language.getActualLanguage()));
            language.setxAbility(getByteSkillLevel(language.getActualXAbility()));
            language.setRwAbility(getByteSkillLevel(language.getActualRwAbility()));
            applicantDao.save(language);
            //int language_id = applicantDao.save(language);
            //language.setLanguageId(language_id);
        }
        return true;
    }

    public boolean saveSkill(String str_skill){
        List<Skill> skillList = JSON.parseArray(str_skill,Skill.class);
        for (Skill skill:skillList){
            skill.setSkillName(getIntSkillName(skill.getActualSkillName()));
            skill.setLevel(getByteSkillLevel(skill.getActualLevel()));
            applicantDao.save(skill);
//            int skill_id = applicantDao.save(skill);
//            skill.setSkillId(skill_id);
        }
        return true;
    }


    public boolean saveWorkingExperience(int user_id,String str_workexp) throws ParseException {

        List<WorkingExperience> workingExperienceList = JSON.parseArray(str_workexp,WorkingExperience.class);
        for (WorkingExperience workingExperience :workingExperienceList){
            workingExperience.setEnterpriseType(getIntEnterpriseType(workingExperience.getActualEnterpriseType()));
            workingExperience.setEnterpriseScale(getIntEnterpriseScale(workingExperience.getActualEnterpriseScale()));
            workingExperience.setIndustry(getIntIndustry(workingExperience.getActualIndustry()));
            workingExperience.setFunction(getIntFunction(workingExperience.getActualFunction()));
            workingExperience.setWorkType(getByteWorkType(workingExperience.getActualWorkType()));
            Experience experience = new Experience();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            experience.setStartTime(new Timestamp(format.parse(workingExperience.getStartTime()).getTime()));
            experience.setEndTime(new Timestamp(format.parse(workingExperience.getEndTime()).getTime()));
            experience.setUserId(user_id);
            int experience_id = applicantDao.save(experience);
            workingExperience.setWexperienceId(experience_id);
            applicantDao.save(workingExperience);
//            int wexperience_id = applicantDao.save(workingExperience);
        }
        return true;
    }



    public boolean saveEducationExperience(int user_id,String str_eduexp) throws ParseException {
        List<EducationExperience> educationExperienceList = JSON.parseArray(str_eduexp,EducationExperience.class);
        for (EducationExperience educationExperience :educationExperienceList){
            educationExperience.setStudentType(getIntStdType(educationExperience.getActualStudentType()));
            educationExperience.setDegree(getIntDegree(educationExperience.getActualDegree()));
            Experience experience = new Experience();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            experience.setStartTime(new Timestamp(format.parse(educationExperience.getStartTime()).getTime()));
            experience.setEndTime(new Timestamp(format.parse(educationExperience.getEndTime()).getTime()));
            experience.setUserId(user_id);
            int experience_id = applicantDao.save(experience);
            educationExperience.setEexperienceId(experience_id);
            applicantDao.save(educationExperience);
//            int eexperience_id = applicantDao.save(educationExperience);
        }
        return true;

    }



    //将JSON对象转换为Java对象
    private <T> Object jsonTojava(String str, Class<T> tClass){
        JSONObject jsonObject = JSON.parseObject(str);
        Object object = jsonObject.toJavaObject(tClass.getClass());
        return object;
    }

    private byte getBytegender(String gender){
        byte sex;
        if (gender.equals("男")) sex = 0;
        else sex = 1;
        return sex;

    }

    public int getIntDomicile(String domicile){
        List<Dictionary> domiciles = DataUtil.allCities();
        int doml = getIntAttribute(domicile,domiciles);
        return doml;
    }

    public byte getByteWorkStatus(String state){
        return (byte)1;
    }


    public byte getByteWorkType(String wt){
        byte ewt = 0;
        switch (wt){
            case "全职": ewt=1; break;
            case "兼职": ewt=2; break;
            case "实习": ewt=3; break;

        }
        return ewt;
    }

    public int getIntLanguage(String lgn){
        List<Dictionary> list = DataUtil.allLanguages();
        int language = getIntAttribute(lgn,list);
        return language;

    }

    public byte getByteSkillLevel(String level){
        byte asl=0;
        switch (level){
            case "一般": asl=1; break;
            case "良好": asl=2; break;
            case "熟练": asl=3; break;
            case "精通": asl=4; break;
        }
        return asl;
    }

    public int getIntSkillName(String s_name){
        List<Dictionary> skills = DataUtil.allSkills();
        int skill_name = getIntAttribute(s_name,skills);
        return skill_name;

    }

    public int getIntEnterpriseType(String etype){
        List<Dictionary> enterprisetypes = DataUtil.allEnterpriseType();
        int enterprisetype = getIntAttribute(etype,enterprisetypes);
        return enterprisetype;

    }

    public int getIntEnterpriseScale(String escale){
        List<Dictionary> scales = DataUtil.allScales();
        int entscale = getIntAttribute(escale,scales);
        return  entscale;
    }

    public int getIntIndustry(String industry){
        List<Dictionary> industries = DataUtil.allIndystries();
        int industryyy = getIntAttribute(industry,industries);
        return industryyy;
    }

    public int getIntFunction(String fuc){
        List<Dictionary> fuctions = DataUtil.allFuctions();
        int fuction = getIntAttribute(fuc,fuctions);
        return fuction;

    }

    public int getIntStdType(String typ){
        int type;
        switch (typ){
            case "统招":type = 1; break;
            default:type = 2; break;
        }
        return type;
    }
    public int getIntDegree(String dg){
        List<Dictionary> degrees = DataUtil.allDegrees();
        int degree = getIntAttribute(dg,degrees);
        return degree;
    }

    //将int转为String
    public String intToString(int attribute, List<Dictionary> list){
        for (int i = 0;i<list.size();i++){
            int attr_id = list.get(i).getDictionaryId();
            if (attr_id ==attribute){return list.get(i).getDictionaryName();}
        }
        return null;
    }

    //由String类型的属性获得int类型的属性
    public int getIntAttribute(String attribute,List<Dictionary> list){
        for (int i=0;i<list.size();i++){
            Dictionary dict = list.get(i);
            String attrbt = dict.getDictionaryName();
            if (attribute.contains(attrbt)) return dict.getDictionaryId();
        }
        return 0;
    }
}













