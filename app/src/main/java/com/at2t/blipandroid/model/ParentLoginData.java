package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class ParentLoginData {
    @SerializedName("parentId")
    private Integer parentId;

    @SerializedName("sectionId")
    private Integer sectionId;

    @SerializedName("relTenantInstitutionId")
    private Integer relTenantInstitutionId;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("role")
    private String role;

    @SerializedName("isFirstLogin")
    private boolean isFirstLogin;

    @SerializedName("institutionName")
    private String institutionName;

    /**
     * No args constructor for use in serialization
     *
     */
    public ParentLoginData() {
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param relTenantInstitutionId
     * @param role
     * @param sectionId
     * @param parentId
     */
    public ParentLoginData(Integer parentId, Integer sectionId, Integer relTenantInstitutionId, String firstName, String lastName, String role) {
        super();
        this.parentId = parentId;
        this.sectionId = sectionId;
        this.relTenantInstitutionId = relTenantInstitutionId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getRelTenantInstitutionId() {
        return relTenantInstitutionId;
    }

    public void setRelTenantInstitutionId(Integer relTenantInstitutionId) {
        this.relTenantInstitutionId = relTenantInstitutionId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
