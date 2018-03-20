package com._51job.web;

import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.domain.User;
        import com._51job.service.EnterpriseService;
import com._51job.tool.DataUtil;
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
@RequestMapping(value = "/company")
public class EnterpriseController {
    private final EnterpriseService enterpriseService;
    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService){
        this.enterpriseService=enterpriseService;
    }

    //保存/修改企业信息
    @RequestMapping(value = "/saveInfo",method = RequestMethod.POST)
    @ResponseBody
    public boolean saveInfo(String str_enterprise,HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession();
        Enterprise enterprise = (Enterprise) session.getAttribute("enterprise");
        int enterprise_id = enterprise.getEnterpriseId();
        boolean result = enterpriseService.saveOrupdateInfo(str_enterprise,enterprise_id);
        return result;
    }

    //保存/修改招聘信息
    @RequestMapping(value = "/saveJob",method = RequestMethod.POST)
    @ResponseBody
    public boolean saveJob(String str_recruitment) throws ParseException {
        boolean result = enterpriseService.saveOrupdateRecruitment(str_recruitment);
        return true;
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
        PostInfo list=enterpriseService.getPost(company_id);
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
    public ResumeInfo resumeDetail(Integer resumeId,HttpServletRequest request){
        //return：JSON，简历详情
        if(resumeId==null){
            HttpSession session=request.getSession();
            User user=(User) session.getAttribute("user");
            resumeId=user.getUserId();
        }
        ResumeInfo resumeInfo;
        if(request.getSession().getAttribute("user")==null)resumeInfo=enterpriseService.getSpecificResume(resumeId,true);
        else resumeInfo=enterpriseService.getSpecificResume(resumeId,false);
        return resumeInfo;
    }

    //改变投递的简历的状态
    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    @ResponseBody
    public int changeState(int state,int resumeId){
        //返回简历状态
        return enterpriseService.changeRecruitState(state,resumeId);
    }

    //关闭招聘信息
    @RequestMapping(value = "/closeJob", method = RequestMethod.POST)
    @ResponseBody
    public boolean closeJob(int jobId){
        //返回关闭结果
        System.out.println("D1-start");
        boolean result=enterpriseService.closeRecruit(jobId);
        return result;
    }

    //开启招聘
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
        if(id!=0)return enterpriseService.enterprise(id);
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
