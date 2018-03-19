package com._51job.web;

import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.domain.User;
import com._51job.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/common")
public class CommonController {
    private final CommonService commonService;
    @Autowired
    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }
    //登陆
    @RequestMapping(value="/signin",method = RequestMethod.GET)
    @ResponseBody
    public int signin(String account, String password, HttpServletRequest request){
        User user=commonService.signin(account,password);
        if(user==null)
            return 0;
        else{
            HttpSession session=request.getSession();
            session.setAttribute("user",user);
            return user.getRole();
        }
    }
    //获取用户信息（没有登陆则返回null）
    @RequestMapping(value="/userInfo",method = RequestMethod.GET)
    @ResponseBody
    public Enterprise userInfo(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");
        if(user==null) return null;
        else return commonService.userInfo(user.getUserId());
    }

    //账户查重（true:用户名可用；false:用户名重复）
    @RequestMapping(value="/checkAccount",method = RequestMethod.GET)
    @ResponseBody
    public boolean checkAccount(String account){
        return commonService.checkAccount(account);
    }

    // 获取岗位详情
    @RequestMapping(value="/job",method = RequestMethod.GET)
    @ResponseBody
    public Recruitment job(int recruitment_id){
        return commonService.job(recruitment_id);
    }

    //搜索岗位
    @RequestMapping(value="/search",method = RequestMethod.GET)
    @ResponseBody
    public List<SearchResults> search(String city, String key, int salary, String degree, int seniority, int page, int count) throws InterruptedException {
        return commonService.search(city, key, salary, degree, seniority);
    }
    //  获取岗位类型列表
    @RequestMapping(value="/jobNavi",method = RequestMethod.GET)
    @ResponseBody
    public void jobNavi(){
        //return：JSON，岗位类型列表
    }

    @RequestMapping(value = "/front")
    public String frontPage(){
        return "job_search";
    }

    @RequestMapping(value = "/loginPage")
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/resumePage")
    public String resumePage(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");
        if(user==null)return "login";
        else{
            if(user.getRole()==1)return "resume_info";
            else return "company_info";
        }
    }
    @RequestMapping(value = "/recommendPage")
    public String recommendPage(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");
        if(user!=null&&user.getRole()==1)return "job_recommend";
        if(user!=null&&user.getRole()==2) return "company_info";
        if(user==null)return "job_recommend";
        return "";
    }
    @RequestMapping(value = "/applicationPage")
    public String applicationPage(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");
        if(user==null)return "login";
        else{
            if(user.getRole()==1)return "application";
            else return "company_info";
        }
    }
    @RequestMapping(value = "/companyPage")
    public String companyPage(){
        return "company_info";
    }
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public User getUser(HttpServletRequest request){
        HttpSession session=request.getSession();
        return session.getAttribute("user")!=null?(User)session.getAttribute("user"):null;
    }
    @RequestMapping(value = "/recPage")
    public String recInfo(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute("user")==null)return "login";
        else {
            User user=(User) session.getAttribute("user");
            if(user.getRole()==2) return "job_list";
            else return "company_info";
        }
    }
    @RequestMapping(value = "/resumeList")
    public ModelAndView resumeList(int id){
        ModelAndView modelAndView=new ModelAndView("resume_list");
        modelAndView.addObject("id",id);
        return modelAndView;
    }
    @RequestMapping(value = "/jobPage")
    public ModelAndView jobPage(int id){
        ModelAndView modelAndView=new ModelAndView("job_detail");
        modelAndView.addObject("id",id);
        return modelAndView;
    }
    @RequestMapping(value = "/resumeRevise")
    public String resumeRevice(){
        return "resume_revise";
    }
    @RequestMapping(value = "/companyRevise")
    public String companyRevise(){return "company_info_revise";}
    @RequestMapping(value = "/resume_company")
    public String resumeCompany(){
        return "resume_info_company";
    }
}
