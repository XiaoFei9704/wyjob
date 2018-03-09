package com._51job.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Application {
    private int applicationId;
    private int applicantId;
    private int recruitmentId;
    private Timestamp applicationTime;
    private int applicationState;
    private Applicant applicantByApplicantId;
    private Recruitment recruitmentByRecruitmentId;

    @Id
    @Column(name = "application_id", nullable = false)
    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    @Basic
    @Column(name = "applicant_id", nullable = false)
    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    @Basic
    @Column(name = "recruitment_id", nullable = false)
    public int getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(int recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    @Basic
    @Column(name = "application_time", nullable = false)
    public Timestamp getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Timestamp applicationTime) {
        this.applicationTime = applicationTime;
    }

    @Basic
    @Column(name = "application_state", nullable = false)
    public int getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(int applicationState) {
        this.applicationState = applicationState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return applicationId == that.applicationId &&
                applicantId == that.applicantId &&
                recruitmentId == that.recruitmentId &&
                applicationState == that.applicationState &&
                Objects.equals(applicationTime, that.applicationTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationId, applicantId, recruitmentId, applicationTime, applicationState);
    }

    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "user_id", nullable = false)
    public Applicant getApplicantByApplicantId() {
        return applicantByApplicantId;
    }

    public void setApplicantByApplicantId(Applicant applicantByApplicantId) {
        this.applicantByApplicantId = applicantByApplicantId;
    }

    @ManyToOne
    @JoinColumn(name = "recruitment_id", referencedColumnName = "recruitment_id", nullable = false)
    public Recruitment getRecruitmentByRecruitmentId() {
        return recruitmentByRecruitmentId;
    }

    public void setRecruitmentByRecruitmentId(Recruitment recruitmentByRecruitmentId) {
        this.recruitmentByRecruitmentId = recruitmentByRecruitmentId;
    }
}
