package com._51job.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
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
    private String userName;
    private String userMobile;
    private User userByEnterpriseId;
    private Dictionary dictionaryByIndustry;
    private Dictionary dictionaryByType;
    private Collection<Recruitment> recruitmentsByEnterpriseId;

    @Id
    @Column(name = "enterprise_id", nullable = false)
    public int getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "domicile", nullable = false)
    public int getDomicile() {
        return domicile;
    }

    public void setDomicile(int domicile) {
        this.domicile = domicile;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 45)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "founding_time", nullable = true)
    public Timestamp getFoundingTime() {
        return foundingTime;
    }

    public void setFoundingTime(Timestamp foundingTime) {
        this.foundingTime = foundingTime;
    }

    @Basic
    @Column(name = "scale", nullable = false)
    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Basic
    @Column(name = "industry", nullable = false)
    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 45)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "user_mobile", nullable = true, length = 15)
    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
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
                Objects.equals(userName, that.userName) &&
                Objects.equals(userMobile, that.userMobile);
    }

    @Override
    public int hashCode() {

        return Objects.hash(enterpriseId, name, domicile, address, foundingTime, scale, industry, type, description, userName, userMobile);
    }

    @OneToOne
    @JoinColumn(name = "enterprise_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByEnterpriseId() {
        return userByEnterpriseId;
    }

    public void setUserByEnterpriseId(User userByEnterpriseId) {
        this.userByEnterpriseId = userByEnterpriseId;
    }

    @ManyToOne
    @JoinColumn(name = "industry", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryByIndustry() {
        return dictionaryByIndustry;
    }

    public void setDictionaryByIndustry(Dictionary dictionaryByIndustry) {
        this.dictionaryByIndustry = dictionaryByIndustry;
    }

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "dictionary_id", nullable = false)
    public Dictionary getDictionaryByType() {
        return dictionaryByType;
    }

    public void setDictionaryByType(Dictionary dictionaryByType) {
        this.dictionaryByType = dictionaryByType;
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseId")
    public Collection<Recruitment> getRecruitmentsByEnterpriseId() {
        return recruitmentsByEnterpriseId;
    }

    public void setRecruitmentsByEnterpriseId(Collection<Recruitment> recruitmentsByEnterpriseId) {
        this.recruitmentsByEnterpriseId = recruitmentsByEnterpriseId;
    }
}
