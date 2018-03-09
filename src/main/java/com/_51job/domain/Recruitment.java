package com._51job.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Recruitment {
    private int recruitmentId;
    private int enterpriseId;
    private String post;
    private int function;
    private int salary;
    private Integer minDegree;
    private Integer minSeniority;
    private String description;
    private byte state;
    private Timestamp time;
    private Collection<Application> applicationsByRecruitmentId;
    private Collection<Matrix> matricesByRecruitmentId;
    private Enterprise enterpriseByEnterpriseId;
    private byte type;

    @Id
    @Column(name = "recruitment_id", nullable = false)
    public int getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(int recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    @Basic
    @Column(name = "enterprise_id", nullable = false)
    public int getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
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
    @Column(name = "function", nullable = false)
    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
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
    @Column(name = "min_degree", nullable = true)
    public Integer getMinDegree() {
        return minDegree;
    }

    public void setMinDegree(Integer minDegree) {
        this.minDegree = minDegree;
    }

    @Basic
    @Column(name = "min_seniority", nullable = true)
    public Integer getMinSeniority() {
        return minSeniority;
    }

    public void setMinSeniority(Integer minSeniority) {
        this.minSeniority = minSeniority;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "state", nullable = false)
    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recruitment that = (Recruitment) o;
        return recruitmentId == that.recruitmentId &&
                enterpriseId == that.enterpriseId &&
                function == that.function &&
                salary == that.salary &&
                state == that.state &&
                Objects.equals(post, that.post) &&
                Objects.equals(minDegree, that.minDegree) &&
                Objects.equals(minSeniority, that.minSeniority) &&
                Objects.equals(description, that.description) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(recruitmentId, enterpriseId, post, function, salary, minDegree, minSeniority, description, state, time);
    }

    @OneToMany(mappedBy = "recruitmentByRecruitmentId")
    public Collection<Application> getApplicationsByRecruitmentId() {
        return applicationsByRecruitmentId;
    }

    public void setApplicationsByRecruitmentId(Collection<Application> applicationsByRecruitmentId) {
        this.applicationsByRecruitmentId = applicationsByRecruitmentId;
    }

    @OneToMany(mappedBy = "recruitmentByRecruitmentId")
    public Collection<Matrix> getMatricesByRecruitmentId() {
        return matricesByRecruitmentId;
    }

    public void setMatricesByRecruitmentId(Collection<Matrix> matricesByRecruitmentId) {
        this.matricesByRecruitmentId = matricesByRecruitmentId;
    }

    @ManyToOne
    @JoinColumn(name = "enterprise_id", referencedColumnName = "enterprise_id", nullable = false)
    public Enterprise getEnterpriseByEnterpriseId() {
        return enterpriseByEnterpriseId;
    }

    public void setEnterpriseByEnterpriseId(Enterprise enterpriseByEnterpriseId) {
        this.enterpriseByEnterpriseId = enterpriseByEnterpriseId;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
