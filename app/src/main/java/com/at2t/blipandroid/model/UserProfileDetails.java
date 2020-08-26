package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class UserProfileDetails {
    @SerializedName("admissionId")
    private String admissionId;

    @SerializedName("childId")
    private Integer childId;

    @SerializedName("childrenName")
    private String childrenName;

    @SerializedName("email")
    private String email;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("parentId")
    private Integer parentId;

    @SerializedName("personId")
    private Integer personId;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("relTenantInstitutionId")
    private Integer relTenantInstitutionId;

    @SerializedName("secondaryParentName")
    private String secondaryParentName;

    @SerializedName("secondaryPhoneNumber")
    private String secondaryPhoneNumber;

    @SerializedName("sectionId")
    private Integer sectionId;

    /**
     * No args constructor for use in serialization
     */

    public UserProfileDetails(String admissionId, Integer childId, String childrenName, String email, String firstName, String lastName, Integer parentId, Integer personId, String phoneNumber, Integer relTenantInstitutionId, String secondaryParentName, String secondaryPhoneNumber, Integer sectionId) {
        this.admissionId = admissionId;
        this.childId = childId;
        this.childrenName = childrenName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentId = parentId;
        this.personId = personId;
        this.phoneNumber = phoneNumber;
        this.relTenantInstitutionId = relTenantInstitutionId;
        this.secondaryParentName = secondaryParentName;
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        this.sectionId = sectionId;
    }

    public String getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getChildrenName() {
        return childrenName;
    }

    public void setChildrenName(String childrenName) {
        this.childrenName = childrenName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getRelTenantInstitutionId() {
        return relTenantInstitutionId;
    }

    public void setRelTenantInstitutionId(Integer relTenantInstitutionId) {
        this.relTenantInstitutionId = relTenantInstitutionId;
    }

    public String getSecondaryParentName() {
        return secondaryParentName;
    }

    public void setSecondaryParentName(String secondaryParentName) {
        this.secondaryParentName = secondaryParentName;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
}
