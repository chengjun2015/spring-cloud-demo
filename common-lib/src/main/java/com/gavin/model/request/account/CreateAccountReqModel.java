package com.gavin.model.request.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CreateAccountReqModel implements Serializable {

    @JsonProperty("nick_name")
    @NotNull(message = "nick_name不能为空")
    private String nickName;

    @JsonProperty("password")
    @NotNull(message = "password不能为空")
    private String password;

    @JsonProperty("mobile_phone")
    @NotNull(message = "mobile_phone不能为空")
    private String mobilePhone;

    @JsonProperty("email")
    @NotNull(message = "email不能为空")
    private String email;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
