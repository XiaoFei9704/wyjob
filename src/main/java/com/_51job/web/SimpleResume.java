package com._51job.web;

public class SimpleResume {
    private String name;
    private String gender;
    private String degree;
    private String workStatus;
    private String applicationState;
    private String applicationTime;

    public SimpleResume(String name, String gender, String degree, String workStatus, String applicationState, String applicationTime) {
        this.name = name;
        this.gender = gender;
        this.degree = degree;
        this.workStatus = workStatus;
        this.applicationState = applicationState;
        this.applicationTime = applicationTime;
    }

    public SimpleResume() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(String applicationState) {
        this.applicationState = applicationState;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }
}
