package org.example.j2ee.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "notification")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "id")
    private User receiver;
    @ManyToOne
    @JoinColumn(name = "post", referencedColumnName = "id")
    private Post post;
    @Column
    private String content;
    @Column
    private int is_read;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timeline;

}
