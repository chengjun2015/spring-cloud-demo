package com.gavin.model.request.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
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
}
