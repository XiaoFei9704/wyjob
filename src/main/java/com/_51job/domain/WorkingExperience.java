package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "working_experience", schema = "wyjob", catalog = "")
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
    private Experience experienceByWexperienceId;
    private Dictionary dictionaryByEnterpriseType;
    private Dictionary dictionaryByIndustry;
    private Dictionary dictionaryByFunction;

    @Id
    @Column(name = "wexperience_id", nullable = false)
    public int getWexperienceId() {
        return wexperienceId;
    }

    public void setWexperienceId(int wexperienceId) {
        this.wexperienceId = wexperienceId;
    }

    @Basic
    @Column(name = "enterprise_name", nullable = false, length = 45)
    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    @Basic
    @Column(name = "enterprise_type", nullable = false)
    public int getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(int enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    @Basic
    @Column(name = "enterprise_scale", nullable = false)
    public int getEnterpriseScale() {
        return enterpriseScale;
    }

    public void setEnterpriseScale(int enterpriseScale) {
        this.enterpriseScale = enterpriseScale;
    }

    @Basic
    @Column(name = "industry", nullable = false)
    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    @Basic
    @Column(name = "function", nullable = false)
    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    @Basic
    @Column(name = "post", nullable = false, length = 45)
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Basic
    @Column(name = "salary", nullable = false)
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Basic
    @Column(name = "work_type", nullable = false)
    public byte getWorkType() {
        return workType;
    }

    public void setWorkType(byte workType) {
        this.workType = workType;
    }

    @Basic
    @Column(name = "work_description", nullable = false, length = -1)
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

    @OneToOne
    @JoinColumn(name = "wexperience_id", referencedColumnName = "experience_id", nullable = false)
    public Experience getExperienceByWexperienceId() {
        return experienceByWexperienceId;
    }

    public void setExperienceByWexperienceId(Experience experienceByWexperienceId) {
        this.experienceByWexperienceId = experienceByWexperienceId;
    }

    @ManyToOne
    @JoinColumn(name = "enterprise_type", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryByEnterpriseType() {
        return dictionaryByEnterpriseType;
    }

    public void setDictionaryByEnterpriseType(Dictionary dictionaryByEnterpriseType) {
        this.dictionaryByEnterpriseType = dictionaryByEnterpriseType;
    }

    @ManyToOne
    @JoinColumn(name = "industry", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryByIndustry() {
        return dictionaryByIndustry;
    }

    public void setDictionaryByIndustry(Dictionary dictionaryByIndustry) {
        this.dictionaryByIndustry = dictionaryByIndustry;
    }

    @ManyToOne
    @JoinColumn(name = "function", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryByFunction() {
        return dictionaryByFunction;
    }

    public void setDictionaryByFunction(Dictionary dictionaryByFunction) {
        this.dictionaryByFunction = dictionaryByFunction;
    }
}
