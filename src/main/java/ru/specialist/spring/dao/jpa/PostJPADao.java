package ru.specialist.spring.dao.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.specialist.spring.dao.AbstractDAO;
import ru.specialist.spring.dto.PostDto;
import ru.specialist.spring.entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class PostJPADao implements AbstractDAO<Post> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Post data) {
        em.persist(data);
    }


    @Override
    public List<Post> getAll() {
        //JPQL
        String query = "select p from Post p";
        return em.createQuery(query, Post.class).getResultList();
    }

    @Override
    public Post getById(long id) {
        return em.find(Post.class, id);
    }

    @Override
    public void update(long id, Post data) {
        data.setPostId(id);
        em.merge(data);
    }

    @Override
    public void delete(long id) {
        em.remove(getById(id));
    }


}
