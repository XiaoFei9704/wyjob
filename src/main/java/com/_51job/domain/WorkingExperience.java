package com._51job.domain;

import java.util.Objects;

public class WorkingExperience {
    private int wexperienceId;
    private String enterpriseName;
    private int enterpriseType;
    private int enterpriseScale;
    private int industry;
    private int function;
    private String post;
    private int salary;
    private byte workType;
    private String workDescription;

    private String startTime;
    private String endTime;
    private String actualEnterpriseType;
    private String actualEnterpriseScale;
    private String actualIndustry;
    private String actualFunction;
    private String actualWorkType;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getActualEnterpriseType() {
        return actualEnterpriseType;
    }

    public void setActualEnterpriseType(String actualEnterpriseType) {
        this.actualEnterpriseType = actualEnterpriseType;
    }

    public String getActualEnterpriseScale() {
        return actualEnterpriseScale;
    }

    public void setActualEnterpriseScale(String actualEnterpriseScale) {
        this.actualEnterpriseScale = actualEnterpriseScale;
    }

    public String getActualIndustry() {
        return actualIndustry;
    }

    public void setActualIndustry(String actualIndustry) {
        this.actualIndustry = actualIndustry;
    }

    public String getActualFunction() {
        return actualFunction;
    }

    public void setActualFunction(String actualFunction) {
        this.actualFunction = actualFunction;
    }

    public String getActualWorkType() {
        return actualWorkType;
    }

    public void setActualWorkType(String actualWorkType) {
        this.actualWorkType = actualWorkType;
    }

    public int getWexperienceId() {
        return wexperienceId;
    }

    public void setWexperienceId(int wexperienceId) {
        this.wexperienceId = wexperienceId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public int getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(int enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public int getEnterpriseScale() {
        return enterpriseScale;
    }

    public void setEnterpriseScale(int enterpriseScale) {
        this.enterpriseScale = enterpriseScale;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public byte getWorkType() {
        return workType;
    }

    public void setWorkType(byte workType) {
        this.workType = workType;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingExperience that = (WorkingExperience) o;
        return wexperienceId == that.wexperienceId &&
                enterpriseType == that.enterpriseType &&
                enterpriseScale == that.enterpriseScale &&
                industry == that.industry &&
                function == that.function &&
                salary == that.salary &&
                workType == that.workType &&
                Objects.equals(enterpriseName, that.enterpriseName) &&
                Objects.equals(post, that.post) &&
                Objects.equals(workDescription, that.workDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(wexperienceId, enterpriseName, enterpriseType, enterpriseScale, industry, function, post, salary, workType, workDescription);
    }
}
