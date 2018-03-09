package com._51job.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Matrix {
    private int matrixId;
    private int recruitmentId;
    private int name;
    private Integer time;
    private Integer level;
    private Recruitment recruitmentByRecruitmentId;
    private Dictionary dictionaryByName;

    @Id
    @Column(name = "matrix_id", nullable = false)
    public int getMatrixId() {
        return matrixId;
    }

    public void setMatrixId(int matrixId) {
        this.matrixId = matrixId;
    }

    @Basic
    @Column(name = "recruitment_id", nullable = false)
    public int getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(int recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    @Basic
    @Column(name = "time", nullable = true)
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Basic
    @Column(name = "level", nullable = true)
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

    @ManyToOne
    @JoinColumn(name = "recruitment_id", referencedColumnName = "recruitment_id", nullable = false)
    public Recruitment getRecruitmentByRecruitmentId() {
        return recruitmentByRecruitmentId;
    }

    public void setRecruitmentByRecruitmentId(Recruitment recruitmentByRecruitmentId) {
        this.recruitmentByRecruitmentId = recruitmentByRecruitmentId;
    }

    @ManyToOne
    @JoinColumn(name = "name", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryByName() {
        return dictionaryByName;
    }

    public void setDictionaryByName(Dictionary dictionaryByName) {
        this.dictionaryByName = dictionaryByName;
    }
}
