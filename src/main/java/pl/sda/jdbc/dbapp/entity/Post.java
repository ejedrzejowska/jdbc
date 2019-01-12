package pl.sda.jdbc.dbapp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "posts")
@NamedQueries({
        @NamedQuery(name = "postsLikeTitle", query = "SELECT p FROM Post p WHERE p.title LIKE :title")
})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "content", length = 2000)
    private String body;

}

