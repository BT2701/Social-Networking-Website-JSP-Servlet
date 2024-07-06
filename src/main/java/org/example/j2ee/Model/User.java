package org.example.j2ee.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    @Temporal(TemporalType.DATE)
    private Date birth;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private String gender;
    @Column
    private String avt;
    @Column
    private String desc;
    @Column
    private int isonline;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp last_active;
    @Column
    private String social;
    @Column
    private String education;
    @Column
    private String relationship;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timejoin;
}
