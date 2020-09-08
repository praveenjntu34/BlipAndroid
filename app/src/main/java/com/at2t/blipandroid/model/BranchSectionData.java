package com.at2t.blipandroid.model;

import java.util.List;

public class BranchSectionData {
    private Integer branchId;
    private String branchName;
    private Integer relTenantInstitutionId;
    private List<SectionDetailsData> sections = null;

    public BranchSectionData(Integer branchId, String branchName, Integer relTenantInstitutionId, List<SectionDetailsData> sections) {
        super();
        this.branchId = branchId;
        this.branchName = branchName;
        this.relTenantInstitutionId = relTenantInstitutionId;
        this.sections = sections;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getRelTenantInstitutionId() {
        return relTenantInstitutionId;
    }

    public void setRelTenantInstitutionId(Integer relTenantInstitutionId) {
        this.relTenantInstitutionId = relTenantInstitutionId;
    }

    public List<SectionDetailsData> getSections() {
        return sections;
    }

    public void setSections(List<SectionDetailsData> sections) {
        this.sections = sections;
    }

}


