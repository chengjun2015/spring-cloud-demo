package com.gavin.model.domain.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "nickName", "password", "mobilePhone", "email", "createdTime", "modifiedTime"})
@Data
public class AccountModel implements Serializable {

    private Long id;

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

    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date createdTime;

    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date modifiedTime;
}
