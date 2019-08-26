package com.myawscite.backend.entity;


import javax.persistence.*;

import lombok.Setter;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Id;

@Entity
@Table(name = "user")
@Setter
@Getter
//@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class UserInfo {

    //@Column(name = "uuid", unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    private String id;

    private String email;
    private String password;
    private int payed;

    @Transient
    private String token;



}
