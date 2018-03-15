package com._51job.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Recruitment implements Serializable{
    private int recruitmentId;
    private Integer enterpriseId;
    private String post;
    private int function;
    private int salary;
    private Integer minDegree;
    private Integer minSeniority;
    private String description;
    private byte state;
    private Timestamp time;
    private byte workType;

    public int getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(int recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Integer getMinDegree() {
        return minDegree;
    }

    public void setMinDegree(Integer minDegree) {
        this.minDegree = minDegree;
    }

    public Integer getMinSeniority() {
        return minSeniority;
    }

    public void setMinSeniority(Integer minSeniority) {
        this.minSeniority = minSeniority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public byte getWorkType() {
        return workType;
    }

    public void setWorkType(byte workType) {
        this.workType = workType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recruitment that = (Recruitment) o;
        return recruitmentId == that.recruitmentId &&
                function == that.function &&
                salary == that.salary &&
                state == that.state &&
                workType == that.workType &&
                Objects.equals(enterpriseId, that.enterpriseId) &&
                Objects.equals(post, that.post) &&
                Objects.equals(minDegree, that.minDegree) &&
                Objects.equals(minSeniority, that.minSeniority) &&
                Objects.equals(description, that.description) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(recruitmentId, enterpriseId, post, function, salary, minDegree, minSeniority, description, state, time, workType);
    }
}
