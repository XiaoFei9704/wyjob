package com._51job.web;

import com._51job.domain.User;
import com._51job.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/company")
public class EnterpriseController {
    private final EnterpriseService enterpriseService;
    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService){
        this.enterpriseService=enterpriseService;
    }

    //获取所发布的岗位列表以及状态
    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    @ResponseBody
    public List<PostInfo> jobs(int company_id){
        //指定的岗位列表
        List<PostInfo> list=enterpriseService.getPost(company_id);
        return list;
    }

    //获取指定岗位投递来的简历列表
    @RequestMapping(value = "/resumes", method = RequestMethod.GET)
    @ResponseBody
    public List<SimpleResume> resumes(int jobId){
        //return：JSON，简历列表，简历总数list.size()
        List<SimpleResume> list=enterpriseService.getSpecificPost(jobId);
        return list;
    }

    //获取指定简历详情
    @RequestMapping(value = "/resumeDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResumeInfo resumeDetail(int resumeId){
        //return：JSON，简历详情
        ResumeInfo resumeInfo=enterpriseService.getSpecificResume(resumeId);
        return resumeInfo;
    }

    //改变投递的简历的状态
    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    @ResponseBody
    public int changeState(int state,int resumeId){
        //返回简历状态
        int changedState=enterpriseService.changeRecruitState(state,resumeId);
        return changedState;
    }

    //关闭招聘信息
    @RequestMapping(value = "/closeJob", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    @ResponseBody
    public boolean closeJob(int jobId){
        //返回关闭结果
        System.out.println("D1-start");
        boolean result=enterpriseService.closeRecruit(jobId);
        return result;
    }

    //注册企业
    @RequestMapping(value="/register",method = RequestMethod.GET)
    @ResponseBody
    public boolean companyRegister(String account, String password, String name, HttpServletRequest request){
        boolean flag=false;
        User user= enterpriseService.getCompanyReg(account,password,name);
        if (user==null)  return flag;
        else flag=true;
        HttpSession session=request.getSession();
        session.setAttribute("user",user);
        return flag;
    }
}
