package ru.specialist.spring.dao;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.specialist.spring.config.DataConfig;
import ru.specialist.spring.entity.Post;
import ru.specialist.spring.entity.Users;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataConfig.class)
@Sql(scripts = "classpath:schema.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
public class PostJpaDaoTest {


    private final AbstractDAO<Post> postDao;

    @Autowired
    public PostJpaDaoTest(AbstractDAO<Post> postDao) {
        this.postDao = postDao;
    }

    @Test
    void create(){
        Post post = new Post();
        post.setTitle("Day 4");
        post.setContent("All is ok again");
        post.setDtCreated(LocalDateTime.now());

        postDao.create(post);

        assertEquals("Day 4", postDao.getById(4).getTitle());
    }


    @Test
    void update(){
        Post post = postDao.getById(1);
        post.setTitle("Day 4");
        post.setDtUpdated(LocalDateTime.now());

        postDao.update(1, post);
        assertEquals("Day 4", postDao.getById(1).getTitle());
        assertNotNull(postDao.getById(1).getDtUpdated());
    }

    @Test
    void delete() {
        postDao.delete(1);
        assertEquals(2, postDao.getAll().size());
    }

    @Test
    void postTagComment(){
        Post post = postDao.getById(1);
        assertEquals(3, post.getComments().size());
        assertEquals(2, post.getTags().size());
    }


}
