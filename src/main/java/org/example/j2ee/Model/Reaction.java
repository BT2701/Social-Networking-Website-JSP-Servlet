package org.example.j2ee.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "reaction")
@Data
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "post", referencedColumnName = "id")
    private Post post;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timeline;
}
