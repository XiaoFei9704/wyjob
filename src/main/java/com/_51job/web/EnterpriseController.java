package com._51job.web;

import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.domain.User;
import com._51job.service.ApplicantService;
import com._51job.service.EnterpriseService;
import com._51job.tool.DataUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value = "/company")
public class EnterpriseController {
    private final EnterpriseService enterpriseService;
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService){
        this.enterpriseService=enterpriseService;
    }

    //保存/修改企业信息
    @RequestMapping(value = "/saveInfo",method = RequestMethod.POST)
    @ResponseBody
    public boolean saveInfo(String str_enterprise,HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession();
        Enterprise enterprise = (Enterprise) session.getAttribute("user");
        int enterprise_id = enterprise.getEnterpriseId();
        return enterpriseService.saveOrupdateInfo(str_enterprise,enterprise_id);
    }

    //保存/修改招聘信息
    @RequestMapping(value = "/saveJob",method = RequestMethod.POST)
    @ResponseBody
    public boolean saveJob(String str_recruitment, HttpServletRequest request) throws ParseException {
        User user= (User) request.getSession().getAttribute("user");
        int id;
        if(user==null)return false;
        else id=user.getUserId();
        return enterpriseService.saveOrupdateRecruitment(str_recruitment,id);
    }


    //获取所发布的岗位列表以及状态
    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    @ResponseBody
    public PostInfo jobs(HttpServletRequest request){
        //指定的岗位列表
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");
        if(user==null)return null;
        int company_id=user.getUserId();
        return enterpriseService.getPost(company_id);
    }

    //获取指定岗位投递来的简历列表
    @RequestMapping(value = "/resumes", method = RequestMethod.GET)
    @ResponseBody
    public List<SimpleResume> resumes(int jobId){
        //return：JSON，简历列表，简历总数list.size()
        return enterpriseService.getSpecificPost(jobId);
    }

    //获取指定简历详情
    @RequestMapping(value = "/resumeDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResumeInfo resumeDetail(Integer resumeId,HttpServletRequest request){
        //return：JSON，简历详情
        if(resumeId==null){
            HttpSession session=request.getSession();
            User user=(User) session.getAttribute("user");
            resumeId=user.getUserId();
        }
        if(request.getSession().getAttribute("user")==null)return null;
        ResumeInfo resumeInfo;
        resumeInfo=enterpriseService.getSpecificResume(resumeId);
        Jedis jedis=new Jedis("localhost");
        jedis.select(10);
        if(!jedis.exists("app"+resumeId))applicantService.matchAll(request);
        return resumeInfo;
    }

    //改变投递的简历的状态
    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    @ResponseBody
    public int changeState(int state,int resumeId){
        //返回简历状态
        return enterpriseService.changeRecruitState(state,resumeId);
    }

    //关闭招聘信息，关闭后更新缓存，利用AOP
    @RequestMapping(value = "/closeJob", method = RequestMethod.POST)
    @ResponseBody
    public boolean closeJob(int jobId){
        return enterpriseService.closeRecruit(jobId);
    }

    //开启招聘，开启后更新缓存，利用AOP
    @RequestMapping(value = "/openJob", method = RequestMethod.POST)
    @ResponseBody
    public boolean openJob(int jobId){
        return enterpriseService.openJob(jobId);
    }

    //注册企业
    @RequestMapping(value="/register",method = RequestMethod.POST)
    @ResponseBody
    public boolean companyRegister(String account, String password, String name, HttpServletRequest request){
        User user= enterpriseService.getCompanyReg(account,password,name);
        if (user==null)  return false;
        HttpSession session=request.getSession();
        session.setAttribute("user",user);
        return true;
    }

    //企业详情
    @RequestMapping(value = "/enterprise")
    @ResponseBody
    public Enterprise enterprise(HttpServletRequest request,Integer id){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");
        if(id!=null&&id!=0)return enterpriseService.enterprise(id);
        else return enterpriseService.enterprise(user.getUserId());
    }

    @RequestMapping(value = "/jobDetail")
    @ResponseBody
    public PostInfoState jobDetail(int id, HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        if(user!=null){//保存浏览记录
            DataUtil.saveLog(user.getUserId(),id);
        }
        return enterpriseService.getRecruitment(id);
    }
}
