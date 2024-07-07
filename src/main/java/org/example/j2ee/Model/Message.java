package org.example.j2ee.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity(name = "message")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "id")
    private User receiver;
    @Column
    private String content;
    @ManyToOne
    @JoinColumn(name = "id")
    private Group group;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timeline;
}
