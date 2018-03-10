package com._51job.domain;

import java.util.Objects;

public class PreferredFunction {
    private int pfunctionId;
    private int userId;
    private int function;

    public int getPfunctionId() {
        return pfunctionId;
    }

    public void setPfunctionId(int pfunctionId) {
        this.pfunctionId = pfunctionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferredFunction that = (PreferredFunction) o;
        return pfunctionId == that.pfunctionId &&
                userId == that.userId &&
                function == that.function;
    }

    @Override
    public int hashCode() {

        return Objects.hash(pfunctionId, userId, function);
    }
}
