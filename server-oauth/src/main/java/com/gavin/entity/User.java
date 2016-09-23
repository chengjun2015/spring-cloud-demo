package com.gavin.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
@DynamicInsert
@DynamicUpdate
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @OneToMany(mappedBy = "user")
    private List<Authority> authorities;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "modified_time")
    private Timestamp modifiedTime;

    public User() {
    }

    public Authority addAuthority(Authority authority) {
        getAuthorities().add(authority);
        authority.setUser(this);

        return authority;
    }

    public Authority removeAuthority(Authority authority) {
        getAuthorities().remove(authority);
        authority.setUser(null);

        return authority;
    }

}