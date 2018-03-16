package com._51job.domain;

import java.util.Objects;

public class Language {
    private int languageId;
    private int userId;
    private int language;
    private byte rwAbility;
    private byte xAbility;

    private String actualLanguage;
    private String actualRwAbility;
    private String actualXAbility;

    public String getActualLanguage() {
        return actualLanguage;
    }

    public void setActualLanguage(String actualLanguage) {
        this.actualLanguage = actualLanguage;
    }

    public String getActualRwAbility() {
        return actualRwAbility;
    }

    public void setActualRwAbility(String actualRwAbility) {
        this.actualRwAbility = actualRwAbility;
    }

    public String getActualXAbility() {
        return actualXAbility;
    }

    public void setActualXAbility(String actualXAbility) {
        this.actualXAbility = actualXAbility;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public byte getRwAbility() {
        return rwAbility;
    }

    public void setRwAbility(byte rwAbility) {
        this.rwAbility = rwAbility;
    }

    public byte getxAbility() {
        return xAbility;
    }

    public void setxAbility(byte xAbility) {
        this.xAbility = xAbility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language1 = (Language) o;
        return languageId == language1.languageId &&
                userId == language1.userId &&
                language == language1.language &&
                rwAbility == language1.rwAbility &&
                xAbility == language1.xAbility;
    }

    @Override
    public int hashCode() {

        return Objects.hash(languageId, userId, language, rwAbility, xAbility);
    }
}
