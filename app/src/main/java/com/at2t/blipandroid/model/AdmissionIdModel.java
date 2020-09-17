package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class AdmissionIdModel {

    @SerializedName("data")
    public Data data;

    public AdmissionIdModel(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("admissionId")
        private String admissionId;

        @SerializedName("phoneNumber")
        private String mobileNumber;

        public String getAdmissionId() {
            return admissionId;
        }

        public void setAdmissionId(String admissionId) {
            this.admissionId = admissionId;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }
    }
}
