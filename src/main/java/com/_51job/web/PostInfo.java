package com._51job.web;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;

import javax.persistence.Entity;
//岗位信息类
@Entity
public class PostInfo {
    private Enterprise enterprise;
    private Recruitment recruitment;

    public PostInfo(Enterprise enterprise, Recruitment recruitment) {
        this.enterprise = enterprise;
        this.recruitment = recruitment;
    }

    public PostInfo() { }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Recruitment getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(Recruitment recruitment) {
        this.recruitment = recruitment;
    }
}
