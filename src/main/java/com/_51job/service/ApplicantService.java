package com._51job.service;


import com._51job.dao.ApplicantDao;
import com._51job.domain.*;
import com._51job.web.EnterpriseResume;
import com._51job.web.PostInfoState;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class ApplicantService {

    @Autowired
    private ApplicantDao applicantDao;

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
        List<Enterprise> e_list = null;
        int l_size = r_list.size();
        for (int i = 0; i < l_size; i++) {
            //对recruitment对象进行操作
            int recruitment_id = r_list.get(i).getRecruitmentId();
            int enterprise_id = r_list.get(i).getEnterpriseId();
            Recruitment recruitment = applicantDao.get(Recruitment.class, recruitment_id);
            Recruitment recruitment1 = changeMindegree(recruitment);
            r_list.add(recruitment);
            Enterprise enterprise = returnEnterprise(recruitment);
            e_list.add(enterprise);
            }
            return applicantDao.post_info(r_list, e_list, l_size);
    }

    //获取投递的岗位以及对应的状态
    public List<PostInfoState> sentJobstate(int id){

        List<Application> list = applicantDao.oneToMany(id, Applicant.class, Application.class);
        List<Recruitment> list1 = null;
        List<Enterprise> list2 = null;
        int lsize = list.size();
        for (int i = 0; i < lsize; i++) {
            int recruitment_id = list.get(i).getRecruitmentId();
            Recruitment recruitment = applicantDao.get(Recruitment.class,recruitment_id);
            Recruitment recruitment1 = changeMindegree(recruitment);
            Enterprise enterprise = returnEnterprise(recruitment);
            list2.add(enterprise);

            }
            return applicantDao.post_state(list, list1, list2,lsize);
        }


    //保存求职者简历
    public int saveResume(int user_id,String str_applicant,String str_language,
                String str_skill, String str_workexp,
                String str_eduexp, String prelocation,
                String preindustry, String prefunction){

        int count = 0;

        //Certificate certificate = (Certificate) jsonTojava(str_certifacate,Certificate.class);
        Applicant applicant = (Applicant) jsonTojava(str_applicant,Applicant.class);
        Language language = (Language) jsonTojava(str_language,Language.class);
        Skill skill = (Skill) jsonTojava(str_skill,Skill.class);
        WorkingExperience workingExperience = (WorkingExperience) jsonTojava(str_workexp,WorkingExperience.class);
        EducationExperience educationExperience = (EducationExperience) jsonTojava(str_eduexp,EducationExperience.class);
        //TrainingExperience trainingExperience = (TrainingExperience) jsonTojava(str_trainexp,TrainingExperience.class);
        //ProjectExperience projectExperience = (ProjectExperience) jsonTojava(str_proexp,ProjectExperience.class);
        PreferredLocation preferredLocation = (PreferredLocation) jsonTojava(prelocation,PreferredLocation.class);
        PreferredIndustry preferredIndustry = (PreferredIndustry) jsonTojava(preindustry,PreferredIndustry.class);
        PreferredFunction preferredFunction = (PreferredFunction) jsonTojava(prefunction,PreferredFunction.class);

        int applicant_id = applicantDao.save(applicant);
        applicant.setUserId(applicant_id);
        if (applicant_id  > 0){count++;}
        int language_id = applicantDao.save(language);
        language.setLanguageId(language_id);
        if (language_id  > 0){count++;}
        int skill_id = applicantDao.save(skill);
        skill.setSkillId(skill_id);
        if (skill_id >0){count++;}
        int wexperience_id = applicantDao.save(workingExperience);
        workingExperience.setWexperienceId(wexperience_id);
        if(wexperience_id>0){count++;}
        int eexperience_id = applicantDao.save(educationExperience);
        educationExperience.setEexperienceId(eexperience_id);
        if (eexperience_id>0){count++;}
        int plocation_id = applicantDao.save(preferredLocation);
        preferredLocation.setPlocationId(plocation_id);
        if (plocation_id>0){count++;}
        int pindustry_id = applicantDao.save(preferredIndustry);
        preferredIndustry.setPindustryId(pindustry_id);
        if (plocation_id>0){count++;}
        int pfunction_id = applicantDao.save(preferredIndustry);
        preferredFunction.setPfunctionId(pfunction_id);
        if (pfunction_id>0){count++;}
        return count;
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
        Dictionary dictionary=applicantDao.get(Dictionary.class,min_degree);
        String actualMinDegree = dictionary.getDictionaryName();
        recruitment.setActualMinDegree(actualMinDegree);
        return recruitment;

    }

    //返回Enterprise实例
    public Enterprise returnEnterprise(Recruitment recruitment){

        int enterprise_id = recruitment.getEnterpriseId();
        Enterprise enterprise = applicantDao.get(Enterprise.class,enterprise_id);
        int scale = enterprise.getScale();
        Dictionary dictionary1 = applicantDao.get(Dictionary.class,scale);
        String actualScale = dictionary1.getDictionaryName();
        enterprise.setActualScale(actualScale);
        Integer child_id = enterprise.getIndustry();
        StringBuffer industry = getAllpid(child_id);
        enterprise.setActualIndustry(industry);
        return enterprise;
    }
}













