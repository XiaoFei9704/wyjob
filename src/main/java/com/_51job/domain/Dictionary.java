package com._51job.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Dictionary {
    private int dictionaryId;
    private String dictionaryName;
    private int type;
    private Integer parent;
    private Collection<Applicant> applicantsByDictionaryId;
    private Dictionary dictionaryByParent;
    private Collection<Dictionary> dictionariesByDictionaryId;
    private Collection<Enterprise> enterprisesByDictionaryId;
    private Collection<Enterprise> enterprisesByDictionaryId_0;
    private Collection<Language> languagesByDictionaryId;
    private Collection<Matrix> matricesByDictionaryId;
    private Collection<PreferredFunction> preferredFunctionsByDictionaryId;
    private Collection<PreferredIndustry> preferredIndustriesByDictionaryId;
    private Collection<PreferredLocation> preferredLocationsByDictionaryId;
    private Collection<Skill> skillsByDictionaryId;
    private Collection<WorkingExperience> workingExperiencesByDictionaryId;
    private Collection<WorkingExperience> workingExperiencesByDictionaryId_0;
    private Collection<WorkingExperience> workingExperiencesByDictionaryId_1;

    @Id
    @Column(name = "dictionary_id", nullable = false)
    public int getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    @Basic
    @Column(name = "dictionary_name", nullable = false, length = 20)
    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "parent", nullable = true)
    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dictionary that = (Dictionary) o;
        return dictionaryId == that.dictionaryId &&
                type == that.type &&
                Objects.equals(dictionaryName, that.dictionaryName) &&
                Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dictionaryId, dictionaryName, type, parent);
    }

    @OneToMany(mappedBy = "dictionaryByDomicile")
    public Collection<Applicant> getApplicantsByDictionaryId() {
        return applicantsByDictionaryId;
    }

    public void setApplicantsByDictionaryId(Collection<Applicant> applicantsByDictionaryId) {
        this.applicantsByDictionaryId = applicantsByDictionaryId;
    }

    @ManyToOne
    @JoinColumn(name = "parent", referencedColumnName = "dictionary_id")
    public Dictionary getDictionaryByParent() {
        return dictionaryByParent;
    }

    public void setDictionaryByParent(Dictionary dictionaryByParent) {
        this.dictionaryByParent = dictionaryByParent;
    }

    @OneToMany(mappedBy = "dictionaryByParent")
    public Collection<Dictionary> getDictionariesByDictionaryId() {
        return dictionariesByDictionaryId;
    }

    public void setDictionariesByDictionaryId(Collection<Dictionary> dictionariesByDictionaryId) {
        this.dictionariesByDictionaryId = dictionariesByDictionaryId;
    }

    @OneToMany(mappedBy = "dictionaryByIndustry")
    public Collection<Enterprise> getEnterprisesByDictionaryId() {
        return enterprisesByDictionaryId;
    }

    public void setEnterprisesByDictionaryId(Collection<Enterprise> enterprisesByDictionaryId) {
        this.enterprisesByDictionaryId = enterprisesByDictionaryId;
    }

    @OneToMany(mappedBy = "dictionaryByType")
    public Collection<Enterprise> getEnterprisesByDictionaryId_0() {
        return enterprisesByDictionaryId_0;
    }

    public void setEnterprisesByDictionaryId_0(Collection<Enterprise> enterprisesByDictionaryId_0) {
        this.enterprisesByDictionaryId_0 = enterprisesByDictionaryId_0;
    }

    @OneToMany(mappedBy = "dictionaryByLanguage")
    public Collection<Language> getLanguagesByDictionaryId() {
        return languagesByDictionaryId;
    }

    public void setLanguagesByDictionaryId(Collection<Language> languagesByDictionaryId) {
        this.languagesByDictionaryId = languagesByDictionaryId;
    }

    @OneToMany(mappedBy = "dictionaryByName")
    public Collection<Matrix> getMatricesByDictionaryId() {
        return matricesByDictionaryId;
    }

    public void setMatricesByDictionaryId(Collection<Matrix> matricesByDictionaryId) {
        this.matricesByDictionaryId = matricesByDictionaryId;
    }

    @OneToMany(mappedBy = "dictionaryByFunction")
    public Collection<PreferredFunction> getPreferredFunctionsByDictionaryId() {
        return preferredFunctionsByDictionaryId;
    }

    public void setPreferredFunctionsByDictionaryId(Collection<PreferredFunction> preferredFunctionsByDictionaryId) {
        this.preferredFunctionsByDictionaryId = preferredFunctionsByDictionaryId;
    }

    @OneToMany(mappedBy = "dictionaryByIndustry")
    public Collection<PreferredIndustry> getPreferredIndustriesByDictionaryId() {
        return preferredIndustriesByDictionaryId;
    }

    public void setPreferredIndustriesByDictionaryId(Collection<PreferredIndustry> preferredIndustriesByDictionaryId) {
        this.preferredIndustriesByDictionaryId = preferredIndustriesByDictionaryId;
    }

    @OneToMany(mappedBy = "dictionaryByLoactionId")
    public Collection<PreferredLocation> getPreferredLocationsByDictionaryId() {
        return preferredLocationsByDictionaryId;
    }

    public void setPreferredLocationsByDictionaryId(Collection<PreferredLocation> preferredLocationsByDictionaryId) {
        this.preferredLocationsByDictionaryId = preferredLocationsByDictionaryId;
    }

    @OneToMany(mappedBy = "dictionaryBySkillName")
    public Collection<Skill> getSkillsByDictionaryId() {
        return skillsByDictionaryId;
    }

    public void setSkillsByDictionaryId(Collection<Skill> skillsByDictionaryId) {
        this.skillsByDictionaryId = skillsByDictionaryId;
    }

    @OneToMany(mappedBy = "dictionaryByEnterpriseType")
    public Collection<WorkingExperience> getWorkingExperiencesByDictionaryId() {
        return workingExperiencesByDictionaryId;
    }

    public void setWorkingExperiencesByDictionaryId(Collection<WorkingExperience> workingExperiencesByDictionaryId) {
        this.workingExperiencesByDictionaryId = workingExperiencesByDictionaryId;
    }

    @OneToMany(mappedBy = "dictionaryByIndustry")
    public Collection<WorkingExperience> getWorkingExperiencesByDictionaryId_0() {
        return workingExperiencesByDictionaryId_0;
    }

    public void setWorkingExperiencesByDictionaryId_0(Collection<WorkingExperience> workingExperiencesByDictionaryId_0) {
        this.workingExperiencesByDictionaryId_0 = workingExperiencesByDictionaryId_0;
    }

    @OneToMany(mappedBy = "dictionaryByFunction")
    public Collection<WorkingExperience> getWorkingExperiencesByDictionaryId_1() {
        return workingExperiencesByDictionaryId_1;
    }

    public void setWorkingExperiencesByDictionaryId_1(Collection<WorkingExperience> workingExperiencesByDictionaryId_1) {
        this.workingExperiencesByDictionaryId_1 = workingExperiencesByDictionaryId_1;
    }
}
