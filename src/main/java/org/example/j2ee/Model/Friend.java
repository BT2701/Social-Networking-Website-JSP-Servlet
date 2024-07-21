package org.example.j2ee.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "friend")
@Data
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int user1;
    @Column
    private int user2;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timeline;
    @Column
    private int isfriend;
}
