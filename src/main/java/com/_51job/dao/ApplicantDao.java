
package com._51job.dao;


import com._51job.domain.Application;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.web.EnterpriseResume;
import com._51job.web.PostInfoState;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicantDao extends MyDao{

    @Autowired
    public ApplicantDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    //返回投递岗位及其状态
    //将Application表和Recruitment表合并
    public List<PostInfoState> post_state(List<Application> lst1, List<Recruitment> lst2, List<Enterprise> lst3, int lsize){
        List<PostInfoState> lst = null;
        PostInfoState postInfoState = new PostInfoState();
        for(int i=0;i<lsize;i++){
            postInfoState.setApplication(lst1.get(i));
            postInfoState.setRecruitment(lst2.get(i));
            postInfoState.setEnterprise(lst3.get(i));
            lst.add(postInfoState);
        }
        return lst;
  }
  public List<EnterpriseResume> post_info(List<Recruitment> lst1,List<Enterprise> lst2,int lsize){
        List<EnterpriseResume> lst = null;
        EnterpriseResume enterpriseResume = new EnterpriseResume();
        for (int i=0;i<lsize;i++){
            enterpriseResume.setRecruitment(lst1.get(i));
            enterpriseResume.setEnterprise(lst2.get(i));
            lst.add(enterpriseResume);
        }
        return lst;
  }



}

