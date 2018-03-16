package com._51job.web;

import com._51job.domain.Applicant;
import com._51job.domain.User;
import com._51job.service.ApplicantService;
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
@RequestMapping(value = "/applicant")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

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

    // 获取匹配的岗位
//    @RequestMapping(value = "/suitableJobs", method = RequestMethod.GET)
//    @ResponseBody
//    public List<EnterpriseResume> suitableJobs(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        if (session.getAttribute("user")==null){return null;}
//        else {
//             User user = (User) session.getAttribute("user");
//             int user_id = user.getUserId();
//        }
//    }

    //获取投递了的岗位以及对应的状态ok
    @RequestMapping(value = "/sentJobs",method = RequestMethod.GET)
    @ResponseBody
    public List<PostInfoState> appliedJobs(int user_id){
        System.out.println("开始");
        return applicantService.sentJobstate(user_id);
    }

    //注册ok
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    @ResponseBody
    public boolean register(String account, String password){
        Applicant applicant = applicantService.register(account,password);
        System.out.println(applicant);
        if(applicant!=null){
            return true;
    }
        return false;

    }

}
