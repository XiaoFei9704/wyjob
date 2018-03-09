package com._51job.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Applicant {
    private int userId;
    private String name;
    private byte gender;
    private Integer domicile;
    private Byte workingStatus;
    private String mobile;
    private String email;
    private Byte workType;
    private Integer salaryLowerBound;
    private Timestamp birthdate;
    private User userByUserId;
    private Dictionary dictionaryByDomicile;
    private Collection<Application> applicationsByUserId;
    private Collection<Certificate> certificatesByUserId;
    private Collection<Experience> experiencesByUserId;
    private Collection<Language> languagesByUserId;
    private Collection<PreferredFunction> preferredFunctionsByUserId;
    private Collection<PreferredIndustry> preferredIndustriesByUserId;
    private Collection<PreferredLocation> preferredLocationsByUserId;
    private Collection<Skill> skillsByUserId;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    @Column(name = "gender", nullable = false)
    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "domicile", nullable = true)
    public Integer getDomicile() {
        return domicile;
    }

    public void setDomicile(Integer domicile) {
        this.domicile = domicile;
    }

    @Basic
    @Column(name = "working_status", nullable = true)
    public Byte getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(Byte workingStatus) {
        this.workingStatus = workingStatus;
    }

    @Basic
    @Column(name = "mobile", nullable = true, length = 15)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "work_type", nullable = true)
    public Byte getWorkType() {
        return workType;
    }

    public void setWorkType(Byte workType) {
        this.workType = workType;
    }

    @Basic
    @Column(name = "salary_lower_bound", nullable = true)
    public Integer getSalaryLowerBound() {
        return salaryLowerBound;
    }

    public void setSalaryLowerBound(Integer salaryLowerBound) {
        this.salaryLowerBound = salaryLowerBound;
    }

    @Basic
    @Column(name = "birthdate", nullable = true)
    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Applicant applicant = (Applicant) o;
        return userId == applicant.userId &&
                gender == applicant.gender &&
                Objects.equals(name, applicant.name) &&
                Objects.equals(domicile, applicant.domicile) &&
                Objects.equals(workingStatus, applicant.workingStatus) &&
                Objects.equals(mobile, applicant.mobile) &&
                Objects.equals(email, applicant.email) &&
                Objects.equals(workType, applicant.workType) &&
                Objects.equals(salaryLowerBound, applicant.salaryLowerBound) &&
                Objects.equals(birthdate, applicant.birthdate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, name, gender, domicile, workingStatus, mobile, email, workType, salaryLowerBound, birthdate);
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "domicile", referencedColumnName = "dictionary_id")
    public Dictionary getDictionaryByDomicile() {
        return dictionaryByDomicile;
    }

    public void setDictionaryByDomicile(Dictionary dictionaryByDomicile) {
        this.dictionaryByDomicile = dictionaryByDomicile;
    }

    @OneToMany(mappedBy = "applicantByApplicantId")
    public Collection<Application> getApplicationsByUserId() {
        return applicationsByUserId;
    }

    public void setApplicationsByUserId(Collection<Application> applicationsByUserId) {
        this.applicationsByUserId = applicationsByUserId;
    }

    @OneToMany(mappedBy = "applicantByUserId")
    public Collection<Certificate> getCertificatesByUserId() {
        return certificatesByUserId;
    }

    public void setCertificatesByUserId(Collection<Certificate> certificatesByUserId) {
        this.certificatesByUserId = certificatesByUserId;
    }

    @OneToMany(mappedBy = "applicantByUserId")
    public Collection<Experience> getExperiencesByUserId() {
        return experiencesByUserId;
    }

    public void setExperiencesByUserId(Collection<Experience> experiencesByUserId) {
        this.experiencesByUserId = experiencesByUserId;
    }

    @OneToMany(mappedBy = "applicantByUserId")
    public Collection<Language> getLanguagesByUserId() {
        return languagesByUserId;
    }

    public void setLanguagesByUserId(Collection<Language> languagesByUserId) {
        this.languagesByUserId = languagesByUserId;
    }

    @OneToMany(mappedBy = "applicantByUserId")
    public Collection<PreferredFunction> getPreferredFunctionsByUserId() {
        return preferredFunctionsByUserId;
    }

    public void setPreferredFunctionsByUserId(Collection<PreferredFunction> preferredFunctionsByUserId) {
        this.preferredFunctionsByUserId = preferredFunctionsByUserId;
    }

    @OneToMany(mappedBy = "applicantByUserId")
    public Collection<PreferredIndustry> getPreferredIndustriesByUserId() {
        return preferredIndustriesByUserId;
    }

    public void setPreferredIndustriesByUserId(Collection<PreferredIndustry> preferredIndustriesByUserId) {
        this.preferredIndustriesByUserId = preferredIndustriesByUserId;
    }

    @OneToMany(mappedBy = "applicantByUserId")
    public Collection<PreferredLocation> getPreferredLocationsByUserId() {
        return preferredLocationsByUserId;
    }

    public void setPreferredLocationsByUserId(Collection<PreferredLocation> preferredLocationsByUserId) {
        this.preferredLocationsByUserId = preferredLocationsByUserId;
    }

    @OneToMany(mappedBy = "applicantByUserId")
    public Collection<Skill> getSkillsByUserId() {
        return skillsByUserId;
    }

    public void setSkillsByUserId(Collection<Skill> skillsByUserId) {
        this.skillsByUserId = skillsByUserId;
    }
}
