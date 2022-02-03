package ru.specialist.spring.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.specialist.spring.config.DataConfig;
import ru.specialist.spring.dao.repository.PostRepository;
import ru.specialist.spring.entity.Post;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataConfig.class)
@Sql(scripts = "classpath:schema.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
public class PostRepositoryTest {


    private final PostRepository postRepository;

    @Autowired
    public PostRepositoryTest(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Test
    void create(){
        Post post = new Post();
        post.setTitle("Day 4");
        post.setContent("All is ok again");
        post.setDtCreated(LocalDateTime.now());

        postRepository.save(post);

        assertEquals("Day 4", postRepository.findById(4L).get().getTitle());
    }


    @Test
    void update(){
        Post post = postRepository.findById(1L).get();
        post.setTitle("Day 4");
        post.setDtUpdated(LocalDateTime.now());

        postRepository.save(post);
        assertEquals("Day 4", postRepository.findById(1L).get().getTitle());
        assertNotNull(postRepository.findById(1L).get().getDtUpdated());
    }

    @Test
    void delete() {
        postRepository.deleteById(1L);
        assertEquals(2, postRepository.count());
    }

    @Test
    void postTagComment(){
        Post post = postRepository.findById(1L).get();
        assertEquals(3, post.getComments().size());
        assertEquals(2, post.getTags().size());
    }

    @Test
    void findByTitle(){
        Post post = postRepository.findByTitle("Day 1").get();
        assertEquals("It's all good!", post.getContent());
    }

    @Test
    void findByDtCreatedBetween(){
        LocalDateTime from = LocalDateTime.now().minusDays(3);
        LocalDateTime to = LocalDateTime.now().minusDays(1);

        assertEquals(2,
                postRepository.findByDtCreatedBetween(from, to).size());
    }

    @Test
    void findSortedByTagCount(){
        List<Long> ids = postRepository
                .findSortedTagSorted()
                .stream()
                .map(Post::getPostId)
                .collect(Collectors.toList());

        Assertions.assertIterableEquals(List.of(2L, 3L, 1L), ids);
    }


}
