package com.gavin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authority")
@NamedQuery(name = "Authority.findAll", query = "SELECT a FROM Authority a")
@Data
public class Authority implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "authority")
    private String authority;

    @ManyToOne
    private User user;

    public Authority() {
    }

}