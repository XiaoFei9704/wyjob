package com._51job.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class Applicant {
    private int userId;
    private String name;
    private byte gender;
    private Integer domicile;
    private Byte workingStatus;
    private String mobile;
    private String email;
    private Byte workType;
    private Integer salaryLowerBound;
    private Timestamp birthdate;

    private String actualGender;
    private String actualDomicile;
    private String actualWorkingStatus;
    private String actualWorkType;
    private String actualBirthdate;

    public String getActualGender() {
        return actualGender;
    }

    public void setActualGender(String actualGender) {
        this.actualGender = actualGender;
    }

    public String getActualDomicile() {
        return actualDomicile;
    }

    public void setActualDomicile(String actualDomicile) {
        this.actualDomicile = actualDomicile;
    }

    public String getActualWorkingStatus() {
        return actualWorkingStatus;
    }

    public void setActualWorkingStatus(String actualWorkingStatus) {
        this.actualWorkingStatus = actualWorkingStatus;
    }

    public String getActualWorkType() {
        return actualWorkType;
    }

    public void setActualWorkType(String actualWorkType) {
        this.actualWorkType = actualWorkType;
    }

    public String getActualBirthdate() {
        return actualBirthdate;
    }

    public void setActualBirthdate(String actualBirthdate) {
        this.actualBirthdate = actualBirthdate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public Integer getDomicile() {
        return domicile;
    }

    public void setDomicile(Integer domicile) {
        this.domicile = domicile;
    }

    public Byte getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(Byte workingStatus) {
        this.workingStatus = workingStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getWorkType() {
        return workType;
    }

    public void setWorkType(Byte workType) {
        this.workType = workType;
    }

    public Integer getSalaryLowerBound() {
        return salaryLowerBound;
    }

    public void setSalaryLowerBound(Integer salaryLowerBound) {
        this.salaryLowerBound = salaryLowerBound;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Applicant applicant = (Applicant) o;
        return userId == applicant.userId &&
                gender == applicant.gender &&
                Objects.equals(name, applicant.name) &&
                Objects.equals(domicile, applicant.domicile) &&
                Objects.equals(workingStatus, applicant.workingStatus) &&
                Objects.equals(mobile, applicant.mobile) &&
                Objects.equals(email, applicant.email) &&
                Objects.equals(workType, applicant.workType) &&
                Objects.equals(salaryLowerBound, applicant.salaryLowerBound) &&
                Objects.equals(birthdate, applicant.birthdate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, name, gender, domicile, workingStatus, mobile, email, workType, salaryLowerBound, birthdate);
    }
}
