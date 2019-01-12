package pl.sda.jdbc.dbapp;

import pl.sda.jdbc.dbapp.entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaMain {

    //nazwa z persistence.xml persistence-unit name
    //ctr+alt+c(constance) tworzy stala zmienna
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("my-persistence-unit");  //em nie jest thread safe, nie powinien byc wspoldzielony miedzy watkami

    public static void main(String[] args) {
        createPost("First title", "First body...");
        createPost("Second title", "Second body...");
        createPost("Third title", "Third body...");
        createPost("4 title", "ir body...");
        createPost("5th ir title", "5th body...");
        createPost("Sixth title", "Sixth body...");

//        showOnePost();

//        showAllPost();

//        showSomePost("%title%");
//        showSomePostUniversal("%ir%");
        showSomePostsByNameQuery("%ir%");

        System.out.println("End...");
    }

    private static void showSomePostsByNameQuery(String titlePhrase) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Post> posts = em.createNamedQuery("postsLikeTitle", Post.class)
                .setParameter("title", titlePhrase)
                .getResultList();
        System.out.println(posts);
        em.close();
    }

    private static void showSomePostUniversal(String phrase) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT p FROM Post p WHERE p.title LIKE :phraseOne OR p.body LIKE :phraseTwo";
        List<Post> posts = em.createQuery(query, Post.class)
                .setParameter("phraseOne", phrase)
                .setParameter("phraseTwo", phrase)
                .getResultList();

        System.out.println(posts);

        em.close();
    }

    private static void showSomePost(String titlePhrase) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT p FROM Post p WHERE p.title LIKE :title";
        List<Post> posts = em.createQuery(query, Post.class)
                .setParameter("title", titlePhrase)
                .getResultList();
        System.out.println(posts);
        em.close();
    }

    private static void showAllPost() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        //JPQL java persistence query language
        String query = "SELECT p FROM Post p";
//        TypedQuery<Post> allPostsQuery = em.createQuery(query, Post.class);
//        List<Post> resultList = allPostsQuery.getResultList();
        List<Post> resultList = em
                .createQuery(query, Post.class)
                .getResultList();
        for (Post post : resultList) {
            System.out.println(post);
        }

        em.close();
    }

    private static void showOnePost() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        Post post = em.find(Post.class, 1L);
        System.out.println(post);

        em.close();
    }

    private static void createPost(String title, String body) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);

        em.getTransaction().begin();
        em.persist(post);
        em.getTransaction().commit();
        em.close();
    }
}
