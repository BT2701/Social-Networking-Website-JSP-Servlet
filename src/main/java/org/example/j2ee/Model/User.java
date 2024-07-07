package org.example.j2ee.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    @Temporal(TemporalType.DATE)
    private Date birth;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private String gender;
    @Column
    private String avt;
    @Column
    private String desc;
    @Column
    private int isonline;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp last_active;
    @Column
    private String social;
    @Column
    private String education;
    @Column
    private String relationship;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timejoin;
    @JsonIgnore // để tránh vòng lặp vô hạn khi lấy dữ liệu
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    @JsonIgnore // để tránh vòng lặp vô hạn khi lấy dữ liệu
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    @JsonIgnore // để tránh vòng lặp vô hạn khi lấy dữ liệu
    @OneToMany(mappedBy = "user")
    private List<Group> groups;
    @JsonIgnore // để tránh vòng lặp vô hạn khi lấy dữ liệu
    @OneToMany(mappedBy = "user")
    private List<Joining> joinings;
    @JsonIgnore // để tránh vòng lặp vô hạn khi lấy dữ liệu
    @OneToMany(mappedBy = "receiver")
    private List<Message> receiveMessages;
    @JsonIgnore // để tránh vòng lặp vô hạn khi lấy dữ liệu
    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;
    @JsonIgnore // để tránh vòng lặp vô hạn khi lấy dữ liệu
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;
    @JsonIgnore // để tránh vòng lặp vô hạn khi lấy dữ liệu
    @OneToMany(mappedBy = "user")
    private List<Reaction> reactions;

}
