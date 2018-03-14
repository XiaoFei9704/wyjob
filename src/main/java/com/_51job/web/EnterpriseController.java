package com._51job.web;

import com._51job.service.EnterpriseService;
import com._51job.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/company")
public class EnterpriseController {
    private final EnterpriseService enterpriseService;
    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {

        this.enterpriseService = enterpriseService;
    }

    //保存/修改企业信息
    @RequestMapping(value = "/saveInfo",method = RequestMethod.POST)
    @ResponseBody
    public boolean saveInfo(){
        return false;
    }

    //保存/修改招聘信息
    @RequestMapping(value = "saveJob",method = RequestMethod.POST)
    @ResponseBody
    public boolean saveJob(){
        return true;
    }

    //获取所发布的岗位列表以及状态
    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    @ResponseBody
    public void jobs(int start,int end){
        //return JSON;指定的岗位列表，其他的相关信息，比如发布的岗位总数，岗位开放状态
    }

    //获取指定岗位投递来的简历列表
    @RequestMapping(value = "/resumes", method = RequestMethod.GET)
    @ResponseBody
    public void resumes(int jobId,int start,int end){
        //return：JSON，简历列表，简历总数
    }

    //获取指定简历详情
    @RequestMapping(value = "/resumeDetail", method = RequestMethod.GET)
    @ResponseBody
    public void resumeDetail(int resumeId){
        //return：JSON，简历详情
    }

    //改变投递的简历的状态
    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    @ResponseBody
    public int changeState(int resumeId,int state){
        //返回简历状态
        return 0;
    }

    //关闭招聘信息
    @RequestMapping(value = "/closeJob", method = RequestMethod.POST)
    @ResponseBody
    public boolean closeJob(int jobId){
        //返回关闭结果
        return false;
    }
}
