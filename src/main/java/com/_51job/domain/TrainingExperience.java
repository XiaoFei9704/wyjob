package com._51job.domain;

import java.util.Objects;

public class TrainingExperience {
    private int texperienceId;
    private String name;
    private String lecture;
    private String discription;

    public int getTexperienceId() {
        return texperienceId;
    }

    public void setTexperienceId(int texperienceId) {
        this.texperienceId = texperienceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingExperience that = (TrainingExperience) o;
        return texperienceId == that.texperienceId &&
                Objects.equals(name, that.name) &&
                Objects.equals(lecture, that.lecture) &&
                Objects.equals(discription, that.discription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(texperienceId, name, lecture, discription);
    }
}
