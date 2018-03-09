package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Language {
    private int languageId;
    private int userId;
    private int language;
    private byte rwAbility;
    private byte xAbility;
    private Applicant applicantByUserId;
    private Dictionary dictionaryByLanguage;

    @Id
    @Column(name = "language_id", nullable = false)
    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
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
    @Column(name = "language", nullable = false)
    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    @Basic
    @Column(name = "rw_ability", nullable = false)
    public byte getRwAbility() {
        return rwAbility;
    }

    public void setRwAbility(byte rwAbility) {
        this.rwAbility = rwAbility;
    }

    @Basic
    @Column(name = "x_ability", nullable = false)
    public byte getxAbility() {
        return xAbility;
    }

    public void setxAbility(byte xAbility) {
        this.xAbility = xAbility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language1 = (Language) o;
        return languageId == language1.languageId &&
                userId == language1.userId &&
                language == language1.language &&
                rwAbility == language1.rwAbility &&
                xAbility == language1.xAbility;
    }

    @Override
    public int hashCode() {

        return Objects.hash(languageId, userId, language, rwAbility, xAbility);
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
    @JoinColumn(name = "language", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryByLanguage() {
        return dictionaryByLanguage;
    }

    public void setDictionaryByLanguage(Dictionary dictionaryByLanguage) {
        this.dictionaryByLanguage = dictionaryByLanguage;
    }
}
