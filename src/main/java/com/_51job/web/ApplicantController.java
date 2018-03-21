package com._51job.web;

import com._51job.domain.Applicant;
import com._51job.domain.User;
import com._51job.service.ApplicantService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value = "/applicant")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    //保存/修改简历
    @RequestMapping(value = "/saveResume", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveResume(String str_applicant,String str_language,
                              String str_skill, String str_workexp,
                              String str_eduexp, HttpServletRequest request) throws ParseException {
        User user = (User) request.getSession().getAttribute("user");
        Applicant applicant= JSON.parseObject(str_applicant,Applicant.class);
        user.setName(applicant.getName());
        request.getSession().setAttribute("user",user);
        return user != null && applicantService.saveResume(user.getUserId(), str_applicant, str_language, str_skill, str_workexp, str_eduexp);
    }

    // 职位推荐
    @RequestMapping(value = "/suitableJobs", method = RequestMethod.GET)
    @ResponseBody
    public List<EnterpriseResume> suitableJobs(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("user")==null){return applicantService.suitJobs(0);}
        else {
             User user = (User) session.getAttribute("user");
             int user_id = user.getUserId();
             return applicantService.suitJobs(0);
        }
    }

    //获取投递了的岗位以及对应的状态ok
    @RequestMapping(value = "/sentJobs",method = RequestMethod.GET)
    @ResponseBody
    public List<PostInfoState> appliedJobs(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        if(user==null)return null;
        return applicantService.sentJobstate(user.getUserId());
    }

    //注册ok
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public boolean register(String account, String password,HttpServletRequest request){
        if(applicantService.hasUser(account))return false;
        Applicant applicant = applicantService.register(account,password);
        if(applicant!=null){
            User user=new User();
            user.setUserId(applicant.getUserId());
            user.setUserName(account);
            user.setPassword(password);
            user.setRole(1);
            request.getSession().setAttribute("user",user);
            return true;
        }
        return false;
    }

    //投递岗位
    @RequestMapping(value = "/shenqing", method = RequestMethod.POST)
    @ResponseBody
    public boolean applicate(int id , HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user != null && applicantService.applicate(id, user.getUserId());
    }

}
