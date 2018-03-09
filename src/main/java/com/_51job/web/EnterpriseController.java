package com._51job.web;

import com._51job.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/company")
public class EnterpriseController {
    private final MyService myService;
    @Autowired
    public EnterpriseController(MyService myService) {
        this.myService = myService;
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
