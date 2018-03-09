package com._51job.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/applicant")
public class ApplicantController {
    @RequestMapping(value = "/saveResume", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveResume(){
        return false;
    }

    // 获取匹配的岗位
    @RequestMapping(value = "suitableJobs", method = RequestMethod.GET)
    @ResponseBody
    public void suitableJobs(int start, int end){
        return;
    }

    //获取投递了的岗位以及对应的状态
    @RequestMapping(value = "sentJobs")
    @ResponseBody
    public void appliedJobs(int start, int end){
        return;
    }
}
