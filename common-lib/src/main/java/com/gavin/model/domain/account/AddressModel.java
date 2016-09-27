package com.gavin.model.domain.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AddressModel implements Serializable {

    private Long id;

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
}
