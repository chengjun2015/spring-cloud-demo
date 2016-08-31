package com.gavin.model.request.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CreateAddressReqModel implements Serializable {

    @JsonProperty("account_id")
    @NotNull(message = "account_id不能为空")
    private Long accountId;

    @JsonProperty("consignee")
    @NotNull(message = "consignee不能为空")
    private String consignee;

    @JsonProperty("phoneNumber")
    @NotNull(message = "phoneNumber不能为空")
    private String phoneNumber;

    @JsonProperty("country")
    @NotNull(message = "country不能为空")
    private String country;

    @JsonProperty("province")
    @NotNull(message = "province不能为空")
    private String province;

    @JsonProperty("city")
    @NotNull(message = "city不能为空")
    private String city;

    @JsonProperty("zip_code")
    @NotNull(message = "zip_code不能为空")
    private String zipCode;

    @JsonProperty("address_line")
    @NotNull(message = "address_line不能为空")
    private String addressLine;

    @JsonProperty("default_flag")
    @NotNull(message = "default_flag不能为空")
    private Boolean defaultFlag;

    @JsonProperty("comment")
    private String comment;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public Boolean getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
