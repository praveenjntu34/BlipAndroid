package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class InstructorLoginData {
    @SerializedName("instructorId")
    private int instructorId;

    @SerializedName("sectionId")
    private int sectionId;

    @SerializedName("email")
    private  String email;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("relTenantInstitutionId")
    private String relTenantInstitutionId;

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
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

    public String getRelTenantInstitutionId() {
        return relTenantInstitutionId;
    }

    public void setRelTenantInstitutionId(String relTenantInstitutionId) {
        this.relTenantInstitutionId = relTenantInstitutionId;
    }
}
