package org.example.j2ee.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    @Temporal(TemporalType.DATE)
    private Date birth;

    @Column
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private String gender;

    @Column
    private String avt;

    @Column(name = "`desc`")
    private String desc;

    @Column(name = "isonline", nullable = false)
    private int isOnline;

    @Column(name = "last_active", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp lastActive;

    @Column
    private String social;

    @Column
    private String education;

    @Column
    private String relationship;

    @Column(name = "timejoin")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timeJoin;

    // Quan hệ với các bảng khác
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private List<Group> groups; // Sửa lại thành 'admin'

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Joining> joinings;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private List<Message> receiveMessages;

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private List<Notification> receiveNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Reaction> reactions;

    @Transient
    private Long friendsCount;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Story> stories;
}
