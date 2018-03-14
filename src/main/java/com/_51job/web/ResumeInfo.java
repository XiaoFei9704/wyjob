package com._51job.web;

import com._51job.domain.*;

import java.nio.channels.SelectionKey;
import java.util.List;

public class ResumeInfo {
    private Applicant applicant;
    private List<Experience> experienceList;
    private List<EducationExperience> educationList;
    private List<WorkingExperience> workingList;
    private List<Skill> skillList;
    private List<Language> languageList;
    private List<PreferredFunction> preFunctionList;
    private List<PreferredIndustry> preIndustryList;
    private List<PreferredLocation> preLocationList;

    public ResumeInfo(Applicant applicant, List<Experience> experienceList, List<EducationExperience> educationList, List<WorkingExperience> workingList, List<Skill> skillList, List<Language> languageList, List<PreferredFunction> preFunctionList, List<PreferredIndustry> preIndustryList, List<PreferredLocation> preLocationList) {
        this.applicant = applicant;
        this.experienceList = experienceList;
        this.educationList = educationList;
        this.workingList = workingList;
        this.skillList = skillList;
        this.languageList = languageList;
        this.preFunctionList = preFunctionList;
        this.preIndustryList = preIndustryList;
        this.preLocationList = preLocationList;
    }

    public ResumeInfo() { }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public List<EducationExperience> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EducationExperience> educationList) {
        this.educationList = educationList;
    }

    public List<WorkingExperience> getWorkingList() {
        return workingList;
    }

    public void setWorkingList(List<WorkingExperience> workingList) {
        this.workingList = workingList;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public List<Language> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }

    public List<PreferredFunction> getPreFunctionList() {
        return preFunctionList;
    }

    public void setPreFunctionList(List<PreferredFunction> preFunctionList) {
        this.preFunctionList = preFunctionList;
    }

    public List<PreferredIndustry> getPreIndustryList() {
        return preIndustryList;
    }

    public void setPreIndustryList(List<PreferredIndustry> preIndustryList) {
        this.preIndustryList = preIndustryList;
    }

    public List<PreferredLocation> getPreLocationList() {
        return preLocationList;
    }

    public void setPreLocationList(List<PreferredLocation> preLocationList) {
        this.preLocationList = preLocationList;
    }
}
