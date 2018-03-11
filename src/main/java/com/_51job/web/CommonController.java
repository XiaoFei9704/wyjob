package com._51job.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/common")
public class CommonController {
    //登陆
    @RequestMapping(value="/signin",method = RequestMethod.POST)
    @ResponseBody
    public boolean signin(String account,String password){
        return false;
    }
    //注册
    @RequestMapping(value="/register",method = RequestMethod.POST)
    @ResponseBody
    public boolean register(String account,String password){
        return false;
    }
    //获取用户信息（没有登陆则返回null）
    @RequestMapping(value="/userInfo",method = RequestMethod.GET)
    @ResponseBody
    public Applicant userInfo(){
        return new Applicant();
    }
    //修改密码
    @RequestMapping(value="/changePassword",method = RequestMethod.POST)
    @ResponseBody
    public boolean changePassword(String password){
        return false;
    }
    //用户查重
    @RequestMapping(value="/checkAccount",method = RequestMethod.GET)
    @ResponseBody
    public boolean checkAccount(String account){
        return false;
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
    // 获取岗位详情
    @RequestMapping(value="/job",method = RequestMethod.GET)
    @ResponseBody
    public void job(int jobId){
        //return：JSON，岗位详情
    }
}
