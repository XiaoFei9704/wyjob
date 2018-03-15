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
import java.util.Date;
import java.util.List;



@Service
public class ApplicantService {

    @Autowired
    private ApplicantDao applicantDao;
    //ok
    //求职者注册
    public User register(String account, String password) {
        User user = new User();
        user.setUserName(account);
        user.setPassword(password);
        user.setRole(1);
        int id = applicantDao.save(user);
        user.setUserId(id);
        Applicant applicant = new Applicant();
        applicant.setUserId(id);
        int user_id = applicantDao.save(applicant);
        if (user_id > 0) {
            return user;
        } else return null;
    }

    //列出合适的岗位
    public List<EnterpriseResume> suitJobs(int start, int end) {
        List<Recruitment> r_list = applicantDao.getPart(Recruitment.class, start, end);
        List<EnterpriseResume> lst = null;
        int l_size = r_list.size();
        for (int i = 0; i < l_size; i++) {
            //对recruitment对象进行操作
            int recruitment_id = r_list.get(i).getRecruitmentId();
            Recruitment recruitment = applicantDao.get(Recruitment.class, recruitment_id);
            Recruitment recruitment1 = changeMindegree(recruitment);
            Enterprise enterprise = returnEnterprise(recruitment);
            EnterpriseResume enterpriseResume = new EnterpriseResume();
            enterpriseResume.setRecruitment(recruitment1);
            enterpriseResume.setEnterprise(enterprise);
            lst.add(enterpriseResume);
        }
        return lst;

    }
    //ok
    //获取投递的岗位以及对应的状态
    public List<PostInfoState> sentJobstate(int id){

        List<Application> list = applicantDao.oneToMany(id, Applicant.class, Application.class);
        List<Recruitment> list1 = null;
        List<PostInfoState> lst = null;
        PostInfoState postInfoState = new PostInfoState();
        int lsize = list.size();
        for (int i = 0; i < lsize; i++) {
            Application application = list.get(i);
            postInfoState.setApplication(application);
            int recruitment_id = application.getRecruitmentId();
            Recruitment recruitment = applicantDao.get(Recruitment.class,recruitment_id);
            //获得设置了actualMinDegree的recruitment实例
            Recruitment recruitment1 = changeMindegree(recruitment);
            postInfoState.setRecruitment(recruitment1);
            Enterprise enterprise = returnEnterprise(recruitment);
            postInfoState.setEnterprise(enterprise);
            lst.add(postInfoState);
        }
        return lst;

        }


    //保存求职者简历
    public boolean saveResume(int user_id,String str_applicant,String str_language,
                String str_skill, String str_workexp,
                String str_eduexp, String prelocation,
                String preindustry, String prefunction) throws ParseException {

        boolean r1 = saveApplicant(str_applicant);
        boolean r2 = saveLanguage(str_language);
        boolean r3 =saveSkill(str_skill);
        boolean r4 =saveWorkingExperience(user_id,str_workexp);
        boolean r5 =saveEducationExperience(user_id,str_eduexp);
        boolean r6 =savePreferredLocation(prelocation);
        boolean r7 =savePreferredIndustry(preindustry);
        boolean r8 =savePreferredFunction(prefunction);
        if (r1&&r2&&r3&&r4&&r5&&r6&&r7&&r8)return true;
        return false;
    }

    public boolean saveApplicant(String str_applicant){
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
        Language language = (Language) jsonTojava(str_language,Language.class);
        language.setLanguage(getIntLanguage(language.getActualLanguage()));
        language.setxAbility(getByteSkillLevel(language.getActualXAbility()));
        language.setRwAbility(getByteSkillLevel(language.getActualRwAbility()));
        int language_id = applicantDao.save(language);
        language.setLanguageId(language_id);
        if (language_id  > 0)return true;
        return false;
    }

    public boolean saveSkill(String str_skill){
        Skill skill = (Skill) jsonTojava(str_skill,Skill.class);
        skill.setSkillName(getIntSkillName(skill.getActualSkillName()));
        skill.setLevel(getByteSkillLevel(skill.getActualLevel()));
        int skill_id = applicantDao.save(skill);
        skill.setSkillId(skill_id);
        if (skill_id >0)return true;
        return false;

    }


    public boolean saveWorkingExperience(int user_id,String str_workexp) throws ParseException {
        WorkingExperience workingExperience = (WorkingExperience) jsonTojava(str_workexp,WorkingExperience.class);
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
        int wexperience_id = applicantDao.save(workingExperience);
        if (wexperience_id == experience_id)return true;
        return false;
    }



    public boolean saveEducationExperience(int user_id,String str_eduexp) throws ParseException {
        EducationExperience educationExperience = (EducationExperience) jsonTojava(str_eduexp,EducationExperience.class);
        educationExperience.setStudentType(getIntStdType(educationExperience.getActualStudentType()));
        educationExperience.setDegree(getIntDegree(educationExperience.getActualDegree()));
        Experience experience = new Experience();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        experience.setStartTime(new Timestamp(format.parse(educationExperience.getStartTime()).getTime()));
        experience.setEndTime(new Timestamp(format.parse(educationExperience.getEndTime()).getTime()));
        experience.setUserId(user_id);
        int experience_id = applicantDao.save(experience);
        educationExperience.setEexperienceId(experience_id);
        int eexperience_id = applicantDao.save(educationExperience);
        educationExperience.setEexperienceId(eexperience_id);
        if (eexperience_id == experience_id)return true;
        return false;

    }public boolean savePreferredLocation(String prelocation){
        PreferredLocation preferredLocation = (PreferredLocation) jsonTojava(prelocation,PreferredLocation.class);
        preferredLocation.setLoactionId(getIntDomicile(preferredLocation.getActualLocation()));
        int plocation_id = applicantDao.save(preferredLocation);
        preferredLocation.setPlocationId(plocation_id);
        if (plocation_id>0)return true;
        return false;
    }

    public boolean savePreferredIndustry(String preindustry){
        PreferredIndustry preferredIndustry = (PreferredIndustry) jsonTojava(preindustry,PreferredIndustry.class);
        preferredIndustry.setIndustry(getIntIndustry(preferredIndustry.getActualIndustry()));
        int pindustry_id = applicantDao.save(preferredIndustry);
        preferredIndustry.setPindustryId(pindustry_id);
        if (pindustry_id >0)return true;
        return false;

    }

    public boolean savePreferredFunction(String prefunction){
        PreferredFunction preferredFunction = (PreferredFunction) jsonTojava(prefunction,PreferredFunction.class);
        preferredFunction.setFunction(getIntFunction(preferredFunction.getActualFunction()));
        int pfunction_id = applicantDao.save(preferredFunction);
        preferredFunction.setPfunctionId(pfunction_id);
        if (pfunction_id>0)return true;
        return false;
    }




    //将JSON对象转换为Java对象
    public <T> Object jsonTojava(String str,Class<T> tClass){
        JSONObject jsonObject = JSON.parseObject(str);
        Object object = jsonObject.toJavaObject(tClass.getClass());
        return object;
    }


    public StringBuffer getAllpid(Integer id){
            StringBuffer industry = null;
            while(id != null){
                Dictionary dictionary2 = applicantDao.get(Dictionary.class,id);
                industry.append(dictionary2.getDictionaryName());
                id = dictionary2.getParent();
            }
            return industry;
        }



    //将min_degree转为String
    public Recruitment changeMindegree(Recruitment recruitment){
        //将min_degree由int转为string
        int min_degree = recruitment.getMinDegree();
        List<Dictionary> degrees =DataUtil.allDegrees();
        String actualMinDegree = intToString(min_degree,degrees);
        recruitment.setActualMinDegree(actualMinDegree);
        return recruitment;
    }


    //返回Enterprise实例
    public Enterprise returnEnterprise(Recruitment recruitment){

        int enterprise_id = recruitment.getEnterpriseId();
        Enterprise enterprise = applicantDao.get(Enterprise.class,enterprise_id);
        int scale = enterprise.getScale();
        List<Dictionary> scales = DataUtil.allScales();
        String actualScale = intToString(scale,scales);
        enterprise.setActualScale(actualScale);
        int industry = enterprise.getIndustry();
        List<Dictionary> industries = DataUtil.allIndystries();
        String actualIndustry = intToString(industry,industries);
        enterprise.setActualIndustry(actualIndustry);
        return enterprise;
    }
    public byte getBytegender(String gender){
        byte sex;
        if (gender=="男") sex = 0;
        else sex = 1;
        return sex;

    }

    public int getIntDomicile(String domicile){
        List<Dictionary> domiciles = DataUtil.allCities();
        int doml = getIntAttribute(domicile,domiciles);
        return doml;
    }

    public byte getByteWorkStatus(String state){
        byte status =0;
        switch (state){
            case "离岗": status=1; break;
            case "在岗": status=2; break;
            case "在岗 & 有换岗需求": status=3;break;
        }
        return status;
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
            if (attribute==attrbt) return dict.getDictionaryId();
        }
        return 0;
    }
}













