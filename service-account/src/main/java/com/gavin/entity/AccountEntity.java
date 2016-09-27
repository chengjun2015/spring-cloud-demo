package com.gavin.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "account")
@NamedQuery(name = "AccountEntity.findAll", query = "SELECT a FROM AccountEntity a")
@DynamicInsert
@DynamicUpdate
@Data
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "nick_name")
    private String nickName;

    private String password;

    @Column(name = "created_time", updatable = false)
    private Timestamp createdTime;

    @Column(name = "modified_time", updatable = false)
    private Timestamp modifiedTime;
}