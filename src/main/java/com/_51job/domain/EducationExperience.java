package com._51job.domain;

import java.util.Objects;

public class EducationExperience {
    private int eexperienceId;
    private String name;
    private String major;
    private int studentType;
    private int degree;

    public int getEexperienceId() {
        return eexperienceId;
    }

    public void setEexperienceId(int eexperienceId) {
        this.eexperienceId = eexperienceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getStudentType() {
        return studentType;
    }

    public void setStudentType(int studentType) {
        this.studentType = studentType;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EducationExperience that = (EducationExperience) o;
        return eexperienceId == that.eexperienceId &&
                studentType == that.studentType &&
                degree == that.degree &&
                Objects.equals(name, that.name) &&
                Objects.equals(major, that.major);
    }

    @Override
    public int hashCode() {

        return Objects.hash(eexperienceId, name, major, studentType, degree);
    }
}
