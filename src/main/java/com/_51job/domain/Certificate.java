package com._51job.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Certificate {
    private int certificateId;
    private String name;
    private Date time;
    private int userId;
    private Applicant applicantByUserId;

    @Id
    @Column(name = "certificate_id", nullable = false)
    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
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
    @Column(name = "time", nullable = false)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return certificateId == that.certificateId &&
                userId == that.userId &&
                Objects.equals(name, that.name) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(certificateId, name, time, userId);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public Applicant getApplicantByUserId() {
        return applicantByUserId;
    }

    public void setApplicantByUserId(Applicant applicantByUserId) {
        this.applicantByUserId = applicantByUserId;
    }
}
