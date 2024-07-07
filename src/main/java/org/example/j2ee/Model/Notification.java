package org.example.j2ee.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity(name = "notification")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;
    @Column
    private String content;
    @Column
    private int is_read;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timeline;

}
