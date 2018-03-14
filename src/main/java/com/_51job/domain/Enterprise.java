package com._51job.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class Enterprise {
    private int enterpriseId;
    private String name;
    private int domicile;
    private String address;
    private Timestamp foundingTime;
    private int scale;
    private int industry;
    private int type;
    private String description;
    private String hrName;
    private String hrMobile;


    private String actualDomicile;
    private String actualFoundingTime;
    private String actualScale;
    private String actualIndustry;
    private String actualType;


    public String getActualDomicile() {
        return actualDomicile;
    }

    public void setActualDomicile(String actualDomicile) {
        this.actualDomicile = actualDomicile;
    }

    public String getActualFoundingTime() {
        return actualFoundingTime;
    }

    public void setActualFoundingTime(String actualFoundingTime) {
        this.actualFoundingTime = actualFoundingTime;
    }

    public String getActualScale() {
        return actualScale;
    }

    public void setActualScale(String actualScale) {
        this.actualScale = actualScale;
    }

    public String getActualIndustry() {
        return actualIndustry;
    }

    public void setActualIndustry(StringBuffer actualIndustry) {
        this.actualIndustry = actualIndustry;
    }

    public String getActualType() {
        return actualType;
    }

    public void setActualType(String actualType) {
        this.actualType = actualType;
    }



    public int getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDomicile() {
        return domicile;
    }

    public void setDomicile(int domicile) {
        this.domicile = domicile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getFoundingTime() {
        return foundingTime;
    }

    public void setFoundingTime(Timestamp foundingTime) {
        this.foundingTime = foundingTime;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHrName() {
        return hrName;
    }

    public void setHrName(String hrName) {
        this.hrName = hrName;
    }

    public String getHrMobile() {
        return hrMobile;
    }

    public void setHrMobile(String hrMobile) {
        this.hrMobile = hrMobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enterprise that = (Enterprise) o;
        return enterpriseId == that.enterpriseId &&
                domicile == that.domicile &&
                scale == that.scale &&
                industry == that.industry &&
                type == that.type &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(foundingTime, that.foundingTime) &&
                Objects.equals(description, that.description) &&
                Objects.equals(hrName, that.hrName) &&
                Objects.equals(hrMobile, that.hrMobile);
    }

    @Override
    public int hashCode() {

        return Objects.hash(enterpriseId, name, domicile, address, foundingTime, scale, industry, type, description, hrName, hrMobile);
    }
}
