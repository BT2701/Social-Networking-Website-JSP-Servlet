package org.example.j2ee.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "story")
@Data
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;
    @Column
    private String image;
    @Column
    private Timestamp timeline;
}
