package com._51job.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Experience {
    private int experienceId;
    private int userId;
    private Timestamp startTime;
    private Timestamp endTime;
    private EducationExperience educationExperienceByExperienceId;
    private Applicant applicantByUserId;
    private ProjectExperience projectExperienceByExperienceId;
    private TrainingExperience trainingExperienceByExperienceId;
    private WorkingExperience workingExperienceByExperienceId;

    @Id
    @Column(name = "experience_id", nullable = false)
    public int getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(int experienceId) {
        this.experienceId = experienceId;
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
    @Column(name = "start_time", nullable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = true)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return experienceId == that.experienceId &&
                userId == that.userId &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(experienceId, userId, startTime, endTime);
    }

    @OneToOne(mappedBy = "experienceByEexperienceId")
    public EducationExperience getEducationExperienceByExperienceId() {
        return educationExperienceByExperienceId;
    }

    public void setEducationExperienceByExperienceId(EducationExperience educationExperienceByExperienceId) {
        this.educationExperienceByExperienceId = educationExperienceByExperienceId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public Applicant getApplicantByUserId() {
        return applicantByUserId;
    }

    public void setApplicantByUserId(Applicant applicantByUserId) {
        this.applicantByUserId = applicantByUserId;
    }

    @OneToOne(mappedBy = "experienceByPexperienceId")
    public ProjectExperience getProjectExperienceByExperienceId() {
        return projectExperienceByExperienceId;
    }

    public void setProjectExperienceByExperienceId(ProjectExperience projectExperienceByExperienceId) {
        this.projectExperienceByExperienceId = projectExperienceByExperienceId;
    }

    @OneToOne(mappedBy = "experienceByTexperienceId")
    public TrainingExperience getTrainingExperienceByExperienceId() {
        return trainingExperienceByExperienceId;
    }

    public void setTrainingExperienceByExperienceId(TrainingExperience trainingExperienceByExperienceId) {
        this.trainingExperienceByExperienceId = trainingExperienceByExperienceId;
    }

    @OneToOne(mappedBy = "experienceByWexperienceId")
    public WorkingExperience getWorkingExperienceByExperienceId() {
        return workingExperienceByExperienceId;
    }

    public void setWorkingExperienceByExperienceId(WorkingExperience workingExperienceByExperienceId) {
        this.workingExperienceByExperienceId = workingExperienceByExperienceId;
    }
}
