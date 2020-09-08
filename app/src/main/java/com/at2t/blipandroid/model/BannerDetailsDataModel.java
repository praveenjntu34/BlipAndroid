package com.at2t.blipandroid.model;

public class BannerDetailsDataModel {

    private Integer auditCreatedBy;
    private Integer bannerId;
    private String bannerStream;
    private Integer relTenantInstitutionId;
    private String secondaryBanner;
    private String shortDescription;
    private String title;

    public BannerDetailsDataModel() {
    }

    /**
     *
     * @param relTenantInstitutionId
     * @param auditCreatedBy
     * @param bannerId
     * @param secondaryBanner
     * @param bannerStream
     * @param shortDescription
     * @param title
     */
    public BannerDetailsDataModel(Integer auditCreatedBy, Integer bannerId, String bannerStream, Integer relTenantInstitutionId, String secondaryBanner, String shortDescription, String title) {
        super();
        this.auditCreatedBy = auditCreatedBy;
        this.bannerId = bannerId;
        this.bannerStream = bannerStream;
        this.relTenantInstitutionId = relTenantInstitutionId;
        this.secondaryBanner = secondaryBanner;
        this.shortDescription = shortDescription;
        this.title = title;
    }

    public Integer getAuditCreatedBy() {
        return auditCreatedBy;
    }

    public void setAuditCreatedBy(Integer auditCreatedBy) {
        this.auditCreatedBy = auditCreatedBy;
    }

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerStream() {
        return bannerStream;
    }

    public void setBannerStream(String bannerStream) {
        this.bannerStream = bannerStream;
    }

    public Integer getRelTenantInstitutionId() {
        return relTenantInstitutionId;
    }

    public void setRelTenantInstitutionId(Integer relTenantInstitutionId) {
        this.relTenantInstitutionId = relTenantInstitutionId;
    }

    public String getSecondaryBanner() {
        return secondaryBanner;
    }

    public void setSecondaryBanner(String secondaryBanner) {
        this.secondaryBanner = secondaryBanner;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
