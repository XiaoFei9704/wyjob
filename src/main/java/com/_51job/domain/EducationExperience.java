package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "education_experience", schema = "wyjob", catalog = "")
public class EducationExperience {
    private int eexperienceId;
    private String name;
    private String major;
    private int studentType;
    private int degree;
    private Experience experienceByEexperienceId;

    @Id
    @Column(name = "eexperience_id", nullable = false)
    public int getEexperienceId() {
        return eexperienceId;
    }

    public void setEexperienceId(int eexperienceId) {
        this.eexperienceId = eexperienceId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "major", nullable = false, length = 45)
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Basic
    @Column(name = "student_type", nullable = false)
    public int getStudentType() {
        return studentType;
    }

    public void setStudentType(int studentType) {
        this.studentType = studentType;
    }

    @Basic
    @Column(name = "degree", nullable = false)
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

    @OneToOne
    @JoinColumn(name = "eexperience_id", referencedColumnName = "experience_id", nullable = false)
    public Experience getExperienceByEexperienceId() {
        return experienceByEexperienceId;
    }

    public void setExperienceByEexperienceId(Experience experienceByEexperienceId) {
        this.experienceByEexperienceId = experienceByEexperienceId;
    }
}
