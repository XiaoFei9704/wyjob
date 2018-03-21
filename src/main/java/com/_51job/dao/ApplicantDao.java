
package com._51job.dao;


import com._51job.domain.Application;
import com._51job.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicantDao extends MyDao{

    @Autowired
    public ApplicantDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

//    //返回投递岗位及其状态
//    //将Application表和Recruitment表合并
//    public List<PostInfoState> post_state(List<Application> lst1, List<Recruitment> lst2, List<Enterprise> lst3, int lsize){
//        List<PostInfoState> lst = null;
//        PostInfoState postInfoState = new PostInfoState();
//        for(int i=0;i<lsize;i++){
//            postInfoState.setApplication(lst1.get(i));
//            postInfoState.setRecruitment(lst2.get(i));
//            postInfoState.setEnterprise(lst3.get(i));
//            lst.add(postInfoState);
//        }
//        return lst;
//  }
//  public List<EnterpriseResume> post_info(List<Recruitment> lst1,List<Enterprise> lst2,int lsize){
//      List<EnterpriseResume> lst = null;
//      EnterpriseResume enterpriseResume = new EnterpriseResume();
//      for (int i=0;i<lsize;i++){
//          enterpriseResume.setRecruitment(lst1.get(i));
//          enterpriseResume.setEnterprise(lst2.get(i));
//          lst.add(enterpriseResume);
//      }
//      return lst;
//  }

    public boolean toudi(int a_id, int r_id){
        Query<Application> query=getSession().createQuery("from Application where applicant_id="+a_id+" and recruitment_id="+r_id, Application.class);
        return query.list().size()>0;
    }

    public boolean hasUser(String username){
        Query<User> query=getSession().createQuery("from User where user_name='"+username+"'", User.class);
        return query.list().size()>0;
    }

}

