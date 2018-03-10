package com._51job.domain;

import java.util.Objects;

public class ProjectExperience {
    private int pexperienceId;
    private String projectName;
    private String projectDescription;
    private String duty;
    private String dutyDescription;

    public int getPexperienceId() {
        return pexperienceId;
    }

    public void setPexperienceId(int pexperienceId) {
        this.pexperienceId = pexperienceId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

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
}
