package com.at2t.blipandroid.model;

public class SectionDetailsData {

    private Integer sectionId;
    private Integer branchId;
    private String sectionName;

    /**
     * No args constructor for use in serialization
     */
    public SectionDetailsData() {
    }

    /**
     * @param sectionName
     * @param branchId
     * @param sectionId
     */
    public SectionDetailsData(Integer sectionId, Integer branchId, String sectionName) {
        super();
        this.sectionId = sectionId;
        this.branchId = branchId;
        this.sectionName = sectionName;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
