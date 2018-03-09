package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_experience", schema = "wyjob", catalog = "")
public class ProjectExperience {
    private int pexperienceId;
    private String projectName;
    private String projectDescription;
    private String duty;
    private String dutyDescription;
    private Experience experienceByPexperienceId;

    @Id
    @Column(name = "pexperience_id", nullable = false)
    public int getPexperienceId() {
        return pexperienceId;
    }

    public void setPexperienceId(int pexperienceId) {
        this.pexperienceId = pexperienceId;
    }

    @Basic
    @Column(name = "project_name", nullable = false, length = 45)
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @Column(name = "project_description", nullable = true, length = -1)
    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Basic
    @Column(name = "duty", nullable = false, length = 45)
    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    @Basic
    @Column(name = "duty_description", nullable = true, length = -1)
    public String getDutyDescription() {
        return dutyDescription;
    }

    public void setDutyDescription(String dutyDescription) {
        this.dutyDescription = dutyDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectExperience that = (ProjectExperience) o;
        return pexperienceId == that.pexperienceId &&
                Objects.equals(projectName, that.projectName) &&
                Objects.equals(projectDescription, that.projectDescription) &&
                Objects.equals(duty, that.duty) &&
                Objects.equals(dutyDescription, that.dutyDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pexperienceId, projectName, projectDescription, duty, dutyDescription);
    }

    @OneToOne
    @JoinColumn(name = "pexperience_id", referencedColumnName = "experience_id", nullable = false)
    public Experience getExperienceByPexperienceId() {
        return experienceByPexperienceId;
    }

    public void setExperienceByPexperienceId(Experience experienceByPexperienceId) {
        this.experienceByPexperienceId = experienceByPexperienceId;
    }
}
