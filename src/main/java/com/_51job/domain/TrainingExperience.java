package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "training_experience", schema = "wyjob", catalog = "")
public class TrainingExperience {
    private int texperienceId;
    private String name;
    private String lecture;
    private String discription;
    private Experience experienceByTexperienceId;

    @Id
    @Column(name = "texperience_id", nullable = false)
    public int getTexperienceId() {
        return texperienceId;
    }

    public void setTexperienceId(int texperienceId) {
        this.texperienceId = texperienceId;
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
    @Column(name = "lecture", nullable = false, length = 45)
    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    @Basic
    @Column(name = "discription", nullable = true, length = -1)
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

    @OneToOne
    @JoinColumn(name = "texperience_id", referencedColumnName = "experience_id", nullable = false)
    public Experience getExperienceByTexperienceId() {
        return experienceByTexperienceId;
    }

    public void setExperienceByTexperienceId(Experience experienceByTexperienceId) {
        this.experienceByTexperienceId = experienceByTexperienceId;
    }
}
