package org.example.j2ee.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "joining")
@Data
public class Joining {
    @Id
    @ManyToOne
    @JoinColumn(name = "group", referencedColumnName = "id", nullable = false)
    private Group group;

    @Id
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timeline;
}

