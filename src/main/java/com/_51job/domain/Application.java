package com._51job.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class Application {
    private int applicationId;
    private int applicantId;
    private int recruitmentId;
    private Timestamp applicationTime;
    private int applicationState;

    private String actualApplicationTime;
    private String actualApplicationState;

    public String getActualApplicationTime() {
        return actualApplicationTime;
    }

    public void setActualApplicationTime(String actualApplicationTime) {
        this.actualApplicationTime = actualApplicationTime;
    }

    public String getActualApplicationState() {
        return actualApplicationState;
    }

    public void setActualApplicationState(String actualApplicationState) {
        this.actualApplicationState = actualApplicationState;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(int recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public Timestamp getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Timestamp applicationTime) {
        this.applicationTime = applicationTime;
    }

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
}
