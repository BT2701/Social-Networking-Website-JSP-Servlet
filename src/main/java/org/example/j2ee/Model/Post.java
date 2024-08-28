package org.example.j2ee.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;
    @Column
    private String content;
    @Column
    private String image;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timeline;

    @JsonIgnore // để tránh vòng lặp vô hạn khi lấy dữ liệu
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @JsonIgnore // để tránh vòng lặp vô hạn khi lấy dữ liệu
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<Reaction> reactions;

    @Transient
    private boolean likedByCurrentUser;

    @Transient
    private boolean owner;

    public String getTimePost(){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        long diffInMillis = currentTime.getTime() - timeline.getTime();
        String result= "";
        // Chuyển đổi sự khác biệt từ mili giây thành giờ, phút, giây
        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
        long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
        if (diffInDays > 0) {
            if(diffInDays <= 7){
                result = diffInDays + "d";
            }
            else {
                SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
                result = formatter.format(timeline);
            }
        }
        else if (diffInHours > 0) {
            result= diffInHours + "h";
        } else if (diffInMinutes > 0) {
            result= diffInMinutes + "m";
        } else {
            result= diffInSeconds + "s";
        }
        return result;
    }

    public int getNumComments(){
        return comments.size();
    }
    public int getNumReactions(){
        return reactions.size();
    }
}
