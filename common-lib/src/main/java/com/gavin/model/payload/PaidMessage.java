package com.gavin.model.payload;


import lombok.Data;

import java.io.Serializable;

@Data
public class PaidMessage implements Serializable {

    private Long orderId;

    private Long accountId;
}
