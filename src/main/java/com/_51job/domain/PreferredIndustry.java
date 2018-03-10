package com._51job.domain;

import java.util.Objects;

public class PreferredIndustry {
    private int pindustryId;
    private int userId;
    private int industry;

    public int getPindustryId() {
        return pindustryId;
    }

    public void setPindustryId(int pindustryId) {
        this.pindustryId = pindustryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferredIndustry that = (PreferredIndustry) o;
        return pindustryId == that.pindustryId &&
                userId == that.userId &&
                industry == that.industry;
    }

    @Override
    public int hashCode() {

        return Objects.hash(pindustryId, userId, industry);
    }
}
