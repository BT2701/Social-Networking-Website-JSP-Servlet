package org.example.j2ee.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity(name = "joining")
@Data
public class Joining {
    @Id
    @ManyToOne
    @JoinColumn(name = "id")
    private Group group;
    @Id
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timeline;

}
