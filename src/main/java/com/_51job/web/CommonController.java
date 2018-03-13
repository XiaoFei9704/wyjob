package com._51job.web;

import com._51job.domain.Applicant;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.domain.User;
import com._51job.service.CommonService;
import com._51job.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/common")
public class CommonController {
    private final CommonService commonService;
    @Autowired
    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }
    //登陆
    @RequestMapping(value="/signin",method = RequestMethod.POST)
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
    public void search(String key,int page,int count){
        //return ：JSON，岗位列表
    }
    //  获取岗位类型列表
    @RequestMapping(value="/jobNavi",method = RequestMethod.GET)
    @ResponseBody
    public void jobNavi(){
        //return：JSON，岗位类型列表
    }

    /*//修改密码
    @RequestMapping(value="/changePassword",method = RequestMethod.POST)
    @ResponseBody
    public boolean changePassword(String password){
        return false;
    }*/
}
