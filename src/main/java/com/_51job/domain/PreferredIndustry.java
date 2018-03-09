package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "preferred_industry", schema = "wyjob", catalog = "")
public class PreferredIndustry {
    private int pindustryId;
    private int userId;
    private int industry;
    private Applicant applicantByUserId;
    private Dictionary dictionaryByIndustry;

    @Id
    @Column(name = "pindustry_id", nullable = false)
    public int getPindustryId() {
        return pindustryId;
    }

    public void setPindustryId(int pindustryId) {
        this.pindustryId = pindustryId;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "industry", nullable = false)
    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferredIndustry that = (PreferredIndustry) o;
        return pindustryId == that.pindustryId &&
                userId == that.userId &&
                industry == that.industry;
    }

    @Override
    public int hashCode() {

        return Objects.hash(pindustryId, userId, industry);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public Applicant getApplicantByUserId() {
        return applicantByUserId;
    }

    public void setApplicantByUserId(Applicant applicantByUserId) {
        this.applicantByUserId = applicantByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "industry", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryByIndustry() {
        return dictionaryByIndustry;
    }

    public void setDictionaryByIndustry(Dictionary dictionaryByIndustry) {
        this.dictionaryByIndustry = dictionaryByIndustry;
    }
}
