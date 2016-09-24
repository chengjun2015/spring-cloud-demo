package com.gavin.entity;

import com.gavin.enums.AuthorityEnums;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_authority")
@NamedQuery(name = "UserAuthority.findAll", query = "SELECT u FROM UserAuthority u")
@Data
public class UserAuthority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "authority")
    @Enumerated(EnumType.ORDINAL)
    private AuthorityEnums authority;

    @ManyToOne
    private User user;

    public UserAuthority() {
    }

    @Override
    public String toString() {
        return authority.name();
    }
}