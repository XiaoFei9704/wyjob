package com._51job.web;

import com._51job.domain.Application;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;

import javax.persistence.Entity;
import java.sql.Timestamp;


//岗位信息类
@Entity
public class PostInfoState {
    private Application application;
    private Recruitment recruitment;
    private Enterprise enterprise;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Recruitment getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(Recruitment recruitment) {
        this.recruitment = recruitment;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
}
