package org.example.j2ee.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "`group`")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "admin", referencedColumnName = "id", nullable = false)
    private User admin;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private List<Joining> joinings;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private List<Message> messages;
}
