package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "preferred_function", schema = "wyjob", catalog = "")
public class PreferredFunction {
    private int pfunctionId;
    private int userId;
    private int function;
    private Applicant applicantByUserId;
    private Dictionary dictionaryByFunction;

    @Id
    @Column(name = "pfunction_id", nullable = false)
    public int getPfunctionId() {
        return pfunctionId;
    }

    public void setPfunctionId(int pfunctionId) {
        this.pfunctionId = pfunctionId;
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
    @Column(name = "function", nullable = false)
    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferredFunction that = (PreferredFunction) o;
        return pfunctionId == that.pfunctionId &&
                userId == that.userId &&
                function == that.function;
    }

    @Override
    public int hashCode() {

        return Objects.hash(pfunctionId, userId, function);
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
    @JoinColumn(name = "function", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryByFunction() {
        return dictionaryByFunction;
    }

    public void setDictionaryByFunction(Dictionary dictionaryByFunction) {
        this.dictionaryByFunction = dictionaryByFunction;
    }
}
