package pl.sda.jdbc.dbapp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Setter
@Getter
@ToString
public class Comment extends AuditEntity{

    @Column(length = 1000)
    private String commentBody;

    private String nickname;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post commentedPost;
}
