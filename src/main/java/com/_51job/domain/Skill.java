package com._51job.domain;

import java.util.Objects;

public class Skill {
    private int skillId;
    private int skillName;
    private double time;
    private byte level;
    private int userId;

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getSkillName() {
        return skillName;
    }

    public void setSkillName(int skillName) {
        this.skillName = skillName;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

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
}
