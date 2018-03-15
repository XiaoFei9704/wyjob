package com._51job.web;

import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;

public class SearchResults {
    Recruitment recruitment;
    Enterprise enterprise;

    public SearchResults() {
    }

    public SearchResults(Recruitment recruitment, Enterprise enterprise) {
        this.recruitment = recruitment;
        this.enterprise = enterprise;
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
