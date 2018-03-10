package com._51job.domain;

import java.util.Objects;

public class Matrix {
    private int matrixId;
    private int recruitmentId;
    private int name;
    private Integer time;
    private Integer level;

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

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
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
                name == matrix.name &&
                Objects.equals(time, matrix.time) &&
                Objects.equals(level, matrix.level);
    }

    @Override
    public int hashCode() {

        return Objects.hash(matrixId, recruitmentId, name, time, level);
    }
}
