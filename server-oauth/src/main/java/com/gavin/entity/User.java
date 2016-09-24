package com.gavin.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserAuthority> userAuthorities;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "modified_time")
    private Timestamp modifiedTime;

    public User() {
    }

    public UserAuthority addUserAuthority(UserAuthority userAuthority) {
        if (CollectionUtils.isEmpty(userAuthorities)) {
            userAuthorities = new ArrayList<>();
        }
        getUserAuthorities().add(userAuthority);
        userAuthority.setUser(this);

        return userAuthority;
    }

    public UserAuthority removeUserAuthority(UserAuthority userAuthority) {
        getUserAuthorities().remove(userAuthority);
        userAuthority.setUser(null);

        return userAuthority;
    }

    @Override
    public String toString() {
        return userName;
    }
}