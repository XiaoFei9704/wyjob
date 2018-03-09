package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    private int userId;
    private String userName;
    private String password;
    private Integer role;
    private Applicant applicantByUserId;
    private Enterprise enterpriseByUserId;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 45)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role", nullable = true)
    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, userName, password, role);
    }

    @OneToOne(mappedBy = "userByUserId")
    public Applicant getApplicantByUserId() {
        return applicantByUserId;
    }

    public void setApplicantByUserId(Applicant applicantByUserId) {
        this.applicantByUserId = applicantByUserId;
    }

    @OneToOne(mappedBy = "userByEnterpriseId")
    public Enterprise getEnterpriseByUserId() {
        return enterpriseByUserId;
    }

    public void setEnterpriseByUserId(Enterprise enterpriseByUserId) {
        this.enterpriseByUserId = enterpriseByUserId;
    }
}
