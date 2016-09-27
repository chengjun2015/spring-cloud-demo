package com.gavin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@NamedQuery(name = "AddressEntity.findAll", query = "SELECT a FROM AddressEntity a")
@Data
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "address_line")
    private String addressLine;

    private String city;

    private String comment;

    private String consignee;

    private String country;

    @Column(name = "default_flag")
    private Integer defaultFlag;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String province;

    @Column(name = "zip_code")
    private String zipCode;
}