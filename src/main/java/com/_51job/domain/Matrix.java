package com._51job.domain;

import java.util.Objects;

public class Matrix {
    private int matrixId;
    private int recruitmentId;
    private int skillName;
    private Integer time;
    private Integer level;

    private String actualSkillName;
    private String actualLevel;

    public String getActualSkillName() {
        return actualSkillName;
    }

    public void setActualSkillName(String actualSkillName) {
        this.actualSkillName = actualSkillName;
    }

    public String getActualLevel() {
        return actualLevel;
    }

    public void setActualLevel(String actualLevel) {
        this.actualLevel = actualLevel;
    }

    public int getMatrixId() {
        return matrixId;
    }

    public void setMatrixId(int matrixId) {
        this.matrixId = matrixId;
    }

    public int getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(int recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public int getSkillName() {
        return skillName;
    }

    public void setSkillName(int skillName) {
        this.skillName = skillName;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return matrixId == matrix.matrixId &&
                recruitmentId == matrix.recruitmentId &&
                skillName == matrix.skillName &&
                Objects.equals(time, matrix.time) &&
                Objects.equals(level, matrix.level);
    }

    @Override
    public int hashCode() {

        return Objects.hash(matrixId, recruitmentId, skillName, time, level);
    }
}
