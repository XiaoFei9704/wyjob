package com._51job.web;

import com._51job.domain.User;
import com._51job.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RequestMapping(value = "/applicant")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;
    //OK
    //保存/修改简历
    @RequestMapping(value = "/saveResume", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveResume(int user_id,String str_applicant,String str_language,
                              String str_skill, String str_workexp,
                              String str_eduexp, String prelocation,
                              String preindustry, String prefunction) throws ParseException {

        boolean result  = applicantService.saveResume(user_id,str_applicant, str_language,str_skill,str_workexp,
                str_eduexp,prelocation,preindustry,prefunction);
        return result;
    }
    //OK
    // 获取匹配的岗位
    @RequestMapping(value = "/suitableJobs", method = RequestMethod.GET)
    @ResponseBody
    public List<EnterpriseResume> suitableJobs(int start, int end) {
        if (end <= start) return null;
        else {
            return applicantService.suitJobs(start,end);
        }
    }
    //OK
    //获取投递了的岗位以及对应的状态
    @RequestMapping(value = "/sentJobs")
    @ResponseBody
    public List<PostInfoState> appliedJobs(int user_id){

        return applicantService.sentJobstate(user_id);
    }
    //OK
    //注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public boolean register(String account, String password, HttpServletRequest request){

        User applicant = applicantService.register(account,password);
        if(applicant!=null){
            request.getSession().setAttribute("applicant",applicant);
            return true;
        }
        else return false;

    }
}
