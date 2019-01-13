package pl.sda.jdbc.dbapp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "posts")
@NamedQueries({
        @NamedQuery(name = "postsLikeTitle", query = "SELECT p FROM Post p WHERE p.title LIKE :title")
})
public class Post extends AuditEntity{

    private String title;

    @Column(name = "content", length = 2000)
    private String body;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "commentedPost")  //moze byc tablica kaskad
    //@JoinColumn(name = "postId") po stronie Many - czyli comment
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
    }

}

