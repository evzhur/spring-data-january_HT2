package ru.specialist.spring.dao;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.specialist.spring.config.DataConfig;
import ru.specialist.spring.dto.PostDto;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataConfig.class)
@Sql(scripts = "classpath:schema.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PostJdbcDaoTest {


    private final AbstractDAO<PostDto> postDao;

    @Autowired
    public PostJdbcDaoTest(AbstractDAO<PostDto> postDao) {
        this.postDao = postDao;
    }

    @Test
    void create(){
        PostDto post = new PostDto();
        post.setTitle("Day 4");
        post.setContent("All is ok again");
        post.setDtCreated(LocalDateTime.now());

        postDao.create(post);

        assertEquals("Day 4", postDao.getById(4).getTitle());
    }


    @Test
    void update(){
        PostDto post = postDao.getById(1);
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


}
