package com.at2t.blipandroid.model;

import android.util.Patterns;

import com.google.gson.annotations.SerializedName;

import static com.at2t.blipandroid.utils.Constants.MOBILE_NUMBER_LENGTH;

public class InstructorLoginData {

    private String phoneNumber;
    @SerializedName("instructorId")
    private int instructorUserId;

    @SerializedName("sectionId")
    private int sectionId;

    @SerializedName("email")
    private  String email;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("relTenantInstitutionId")
    private int relTenantInstitutionId;

    @SerializedName("role")
    private String role;

    public int getInstructorUserId() {
        return instructorUserId;
    }

    public void setInstructorUserId(int instructorUserId) {
        this.instructorUserId = instructorUserId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public int getRelTenantInstitutionId() {
        return relTenantInstitutionId;
    }

    public void setRelTenantInstitutionId(int relTenantInstitutionId) {
        this.relTenantInstitutionId = relTenantInstitutionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPhoneNumberValid() {
        return Patterns.PHONE.matcher(getPhoneNumber()).matches()
                && getPhoneNumber().length() == MOBILE_NUMBER_LENGTH;
    }


}
