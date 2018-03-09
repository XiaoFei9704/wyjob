package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Skill {
    private int skillId;
    private int skillName;
    private double time;
    private byte level;
    private int userId;
    private Dictionary dictionaryBySkillName;
    private Applicant applicantByUserId;

    @Id
    @Column(name = "skill_id", nullable = false)
    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    @Basic
    @Column(name = "skill_name", nullable = false)
    public int getSkillName() {
        return skillName;
    }

    public void setSkillName(int skillName) {
        this.skillName = skillName;
    }

    @Basic
    @Column(name = "time", nullable = false, precision = 0)
    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Basic
    @Column(name = "level", nullable = false)
    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return skillId == skill.skillId &&
                skillName == skill.skillName &&
                Double.compare(skill.time, time) == 0 &&
                level == skill.level &&
                userId == skill.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(skillId, skillName, time, level, userId);
    }

    @ManyToOne
    @JoinColumn(name = "skill_name", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryBySkillName() {
        return dictionaryBySkillName;
    }

    public void setDictionaryBySkillName(Dictionary dictionaryBySkillName) {
        this.dictionaryBySkillName = dictionaryBySkillName;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public Applicant getApplicantByUserId() {
        return applicantByUserId;
    }

    public void setApplicantByUserId(Applicant applicantByUserId) {
        this.applicantByUserId = applicantByUserId;
    }
}
