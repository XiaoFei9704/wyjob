package com._51job.web;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;

import javax.persistence.Entity;
import java.util.List;

//岗位信息类
@Entity
public class PostInfo {
    private Enterprise enterprise;
    private List<Recruitment> recruitment;

    public PostInfo(Enterprise enterprise, List<Recruitment> recruitment) {
        this.enterprise = enterprise;
        this.recruitment = recruitment;
    }

    public PostInfo() {
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public List<Recruitment> getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(List<Recruitment> recruitment) {
        this.recruitment = recruitment;
    }
}
