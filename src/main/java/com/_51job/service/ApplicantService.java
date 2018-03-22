package com._51job.service;


import com._51job.dao.ApplicantDao;
import com._51job.dao.EnterpriseDao;
import com._51job.domain.*;
import com._51job.domain.Dictionary;
import com._51job.tool.DataUtil;
import com._51job.tool.Pair;
import com._51job.tool.SerializeUtil;
import com._51job.web.EnterpriseResume;
import com._51job.web.PostInfo;
import com._51job.web.PostInfoState;
import com._51job.web.SearchResults;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ApplicantService {

    @Autowired
    private static Jedis jedis=new Jedis("localhost");
    @Autowired
    private ApplicantDao applicantDao;
    @Autowired
    private EnterpriseDao enterpriseDao;
    @Autowired
    private EnterpriseService enterpriseService;
    private List<String> defaultSkillList=new ArrayList<String>(){{add("SQL");add("Java");add("C/C++");add("HTML5");add("PHP");add("Python");add("Linux ");add("JavaScript");}};
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
        if(userid==0){//没有登录，随机推荐50个
            for(int i=0;i<50;i++){
                Recruitment recruitment=DataUtil.getRecruitment(random.nextInt(90000));
                while (recruitment==null){
                    recruitment=DataUtil.getRecruitment(random.nextInt(90000));
                    if(recruitment!=null)break;
                }
                list.add(new EnterpriseResume(DataUtil.getEnterprise(recruitment.getEnterpriseId()),recruitment));
            }
        }else {
            Jedis jedis=new Jedis("localhost");
            Jedis jedis1=new Jedis("localhost");
            jedis1.select(0);
            jedis.select(10);
            Set<String> keys=jedis.hkeys("app"+userid);
            List<Pair<String,Integer>> scores=new ArrayList<>();
            for(String key: keys){
                scores.add(new Pair<>(key,Integer.parseInt(jedis.hget("app"+userid,key))));
            }
            scores.sort((o1, o2) -> o2.getB()-o1.getB());
            for(int i=0;i<100;i++){
                String key=scores.get(i).getA();
                int score=Integer.parseInt(jedis.hget("app"+userid,key));
                Recruitment recruitment=(Recruitment) SerializeUtil.unserialize((jedis1.get(("rec"+key).getBytes())));
                list.add(new EnterpriseResume(DataUtil.getEnterprise(recruitment.getEnterpriseId()),recruitment,score));
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
            Enterprise enterprise = DataUtil.getEnterprise(recruitment.getEnterpriseId());
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

        boolean r1 = saveApplicant(user_id,str_applicant);
        boolean r2 = saveLanguage(user_id,str_language);
        boolean r3 =saveSkill(user_id,str_skill);
        boolean r4 =saveWorkingExperience(user_id,str_workexp);
        boolean r5 =saveEducationExperience(user_id,str_eduexp);
        if (r1&&r2&&r3&&r4&&r5)return true;
        return false;
    }

    public boolean applicate(int rId, int userId){
        if(applicantDao.toudi(userId,rId))return false;
        Application application=new Application();
        application.setRecruitmentId(rId);
        application.setApplicantId(userId);
        application.setApplicationState(1);
        application.setApplicationTime(new Timestamp(new Date().getTime()));
        int a = applicantDao.save(application);
        return a>0;
    }

    //获取简历信息


    private boolean saveApplicant(int user_id,String str_applicant) throws ParseException {
        JSONObject object=JSON.parseObject(str_applicant);
        Applicant applicant = object.toJavaObject(Applicant.class);
        applicant.setGender(getBytegender(applicant.getActualGender()));
        applicant.setDomicile(getIntDomicile(applicant.getActualDomicile()));
        applicant.setWorkingStatus(getByteWorkStatus(applicant.getActualWorkingStatus()));
        applicant.setWorkType(getByteWorkType(applicant.getActualWorkType()));
        if(applicant.getActualBirthdate()!=null){
            SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
            applicant.setBirthdate(new Timestamp(dateFormat.parse(applicant.getActualBirthdate()).getTime()));
        }
        applicant.setUserId(user_id);
        applicantDao.update(applicant);
        return true;
    }

    public boolean saveLanguage(int userid, String str_language){
        List<Language> languageList = JSON.parseArray(str_language,Language.class);
        for (Language language:languageList){
            language.setUserId(userid);
            language.setLanguage(getIntLanguage(language.getActualLanguage()));
            language.setxAbility(getByteSkillLevel(language.getActualXAbility()));
            language.setRwAbility(getByteSkillLevel(language.getActualRwAbility()));
            applicantDao.save(language);
            //int language_id = applicantDao.save(language);
            //language.setLanguageId(language_id);
        }
        return true;
    }

    public boolean saveSkill(int userid,String str_skill){
        List<Skill> skillList = JSON.parseArray(str_skill,Skill.class);
        for (Skill skill:skillList){
            skill.setUserId(userid);
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

            workingExperience.setFunction(getIntFunction(workingExperience.getActualFunction()));
            workingExperience.setWorkType(getByteWorkType(workingExperience.getActualWorkType()));
            Experience experience = new Experience();
            SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
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
            educationExperience.setDegree(getIntDegree(educationExperience.getActualDegree()));
            Experience experience = new Experience();
            SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
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
    private int getIntAttribute(String attribute, List<Dictionary> list){
        for (int i=0;i<list.size();i++){
            Dictionary dict = list.get(i);
            String attrbt = dict.getDictionaryName();
            if (attrbt.contains(attribute)) return dict.getDictionaryId();
        }
        return 0;
    }

    //判断此岗位是否符合applicant的条件
    private boolean satisfy(Applicant applicant, Recruitment recruitment){
        if(applicant.getSalaryLowerBound()!=null){
            if(recruitment.getSalary()<applicant.getSalaryLowerBound())return false;
        }
        if(applicant.getDomicile()!=null&&applicant.getDomicile()!=0){
            if(DataUtil.getEnterprise(recruitment.getEnterpriseId()).getDomicile()!=applicant.getDomicile())return false;
        }
        return false;
    }
    //获得某个求职者最高学历代码
    public int getHighestDegree(int applicantId){
        List<Experience> exList= applicantDao.getExperienceList(applicantId);
        List<EducationExperience> eduList=applicantDao.getEduList(exList);
        if(eduList==null) return 0;
        int highDegree=1047;
        for(EducationExperience edu:eduList){
            int temp=edu.getDegree();
            if(temp > highDegree) highDegree=temp;
        }
        return highDegree;
    }
    //获得某个求职者工作经验总和
    public long getExperTime(int applicantId){
        long experTime=0;
        List<Experience> exList=applicantDao.getExperienceList(applicantId);
        List<Experience> trainExpList=applicantDao.getTrainList(exList);
        if(trainExpList!=null) {
            for (Experience trainExp:trainExpList) {
                long start=trainExp.getStartTime().getTime();
                long end=trainExp.getEndTime().getTime();
                experTime+=end-start;
            }
            return experTime;
        }
        else return 0;
    }
    //获得某个招聘信息的岗位描述中的技能关键词
    public List<String> getRequiredSkillList(SearchResults rec){
        List<String> requiredSkill=new ArrayList<>();
        for(String skill:defaultSkillList){
            if(rec.getRecruitment().getDescription().toUpperCase().contains(skill.toUpperCase())){
                requiredSkill.add(skill);
            }
        }
        return requiredSkill;
    }

    //求职者简历-招聘信息学历匹配
    public int matchDegree(int highestDegree,int degree){
        return ((highestDegree>degree||highestDegree==degree)?30:0);

    }
    //求职者简历-招聘信息工作经验匹配
    public int matchSeniortity(long trainTime,long time){//time以年为单位
        long requiredTime=time*360*24*3600*1000;
        double rs=(double) trainTime/(double) requiredTime;
        int score=(int) Math.ceil((double) trainTime/(double) requiredTime*30);
        return (trainTime>requiredTime)?30:score;
    }
    //求职者简历-招聘信息最低薪资匹配
    public int matchSalary(int salary,int Rsalary){
        return (salary<Rsalary)?20:5;
    }
    //求职者简历-招聘信息技能匹配
    private int matchSkill(List<String> ownedSkill,SearchResults rec){
        double skillPoint=0;
        int score=0;
        double ratio=0;
        List<String> requiredSkill=getRequiredSkillList(rec);
        for(String skill:ownedSkill){
            if(requiredSkill.contains(skill)) skillPoint+=1;
            int requiredSkillSize=requiredSkill.size();
            if(requiredSkillSize==0)requiredSkillSize=1;
            else ratio=skillPoint/requiredSkillSize;
        }
        score=(int) ratio*20;
        return score;
    }
    //求职者简历-招聘信息匹配
    //学历：30%（求职者最高学历为AG,招聘要求为 RG,(AG>RG)?30:0）
    //最低薪资：20%（求职者要求最低薪资为AS,招聘要求为RG,(AG<RG)?20:5)
    //工作经验：30%(求职者工作经验时间总和为AT,招聘要求为RT,(AT>RT)?20;AT/RT*20)
    //技能：20%
    public void matchAll(HttpServletRequest request){
        int applicantId=((User)request.getSession().getAttribute("user")).getUserId();
        int highestDegree=getHighestDegree(applicantId);
        int salary=applicantDao.getSalary(applicantId);
        long trainTime=getExperTime(applicantId);
        List<String> ownedSkill=applicantDao.getSkillList(applicantId);
        List<SearchResults> list=DataUtil.getResults();
        for(SearchResults rec:list){
            int score=0;
            int degree=rec.getRecruitment().getMinDegree();
            score+=matchDegree(highestDegree,degree);
            long time=rec.getRecruitment().getMinSeniority();
            score+=matchSeniortity(trainTime,time);
            int Rsalary=rec.getRecruitment().getSalary();
            score+=matchSalary(salary,Rsalary);
            score+=matchSkill(ownedSkill,rec);
            String appID="app"+applicantId;
            String recID=String.valueOf(rec.getRecruitment().getRecruitmentId());
            String finalScore=String.valueOf(score);
            jedis.select(10);
            jedis.hset(appID,recID,finalScore);
        }

    }
	
	public boolean hasUser(String username){
        return applicantDao.hasUser(username);
    }
}













