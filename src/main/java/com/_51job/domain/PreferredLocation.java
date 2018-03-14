package com._51job.domain;

import java.util.Objects;

public class PreferredLocation {
    private int plocationId;
    private int loactionId;
    private int userId;

    private String actualLocation;

    public String getActualLocation() {
        return actualLocation;
    }

    public void setActualLocation(String actualLocation) {
        this.actualLocation = actualLocation;
    }

    public int getPlocationId() {
        return plocationId;
    }

    public void setPlocationId(int plocationId) {
        this.plocationId = plocationId;
    }

    public int getLoactionId() {
        return loactionId;
    }

    public void setLoactionId(int loactionId) {
        this.loactionId = loactionId;
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
        PreferredLocation that = (PreferredLocation) o;
        return plocationId == that.plocationId &&
                loactionId == that.loactionId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(plocationId, loactionId, userId);
    }
}
