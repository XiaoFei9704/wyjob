package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "preferred_location", schema = "wyjob", catalog = "")
public class PreferredLocation {
    private int plocationId;
    private int loactionId;
    private int userId;
    private Dictionary dictionaryByLoactionId;
    private Applicant applicantByUserId;

    @Id
    @Column(name = "plocation_id", nullable = false)
    public int getPlocationId() {
        return plocationId;
    }

    public void setPlocationId(int plocationId) {
        this.plocationId = plocationId;
    }

    @Basic
    @Column(name = "loaction_id", nullable = false)
    public int getLoactionId() {
        return loactionId;
    }

    public void setLoactionId(int loactionId) {
        this.loactionId = loactionId;
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
        PreferredLocation that = (PreferredLocation) o;
        return plocationId == that.plocationId &&
                loactionId == that.loactionId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(plocationId, loactionId, userId);
    }

    @ManyToOne
    @JoinColumn(name = "loaction_id", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryByLoactionId() {
        return dictionaryByLoactionId;
    }

    public void setDictionaryByLoactionId(Dictionary dictionaryByLoactionId) {
        this.dictionaryByLoactionId = dictionaryByLoactionId;
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
