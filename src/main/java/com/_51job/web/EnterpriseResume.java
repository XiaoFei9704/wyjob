package com._51job.web;

import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;

public class EnterpriseResume {
    private Enterprise enterprise;
    private Recruitment recruitment;

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
