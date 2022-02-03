package ru.specialist.spring.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.specialist.spring.dao.AbstractDAO;
import ru.specialist.spring.dto.PostDto;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class PostJdbcDao implements AbstractDAO<PostDto> {

    private static final String CREATE = """
                insert into post(title, content, dt_created)
                    values (?, ?, ?);
            """;

    private static final String GET_ALL = """
                select 
                    post_id,
                    title,
                    content,
                    dt_created,
                    dt_updated
                from 
                    post
            """;

    private static final String GET_BY_ID = """
                select 
                    post_id,
                    title,
                    content,
                    dt_created,
                    dt_updated
                from 
                    post
                where
                    post_id = ?
            """;

    private static final String UPDATE = """
                update post
                set 
                    title = ?,
                    content = ?,
                    dt_updated = ?
                where
                    post_id = ? 
            """;

    private static String DELETE = "delete from post where post_id = ?";

    private final RowMapper<PostDto> rowMapper = (rs, rowNum) -> {
        PostDto post = new PostDto();
        post.setPostId(rs.getLong("post_id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setDtCreated(rs.getTimestamp("dt_created").toLocalDateTime());
        Timestamp ts = rs.getTimestamp("dt_updated");
        if (ts != null) {
            post.setDtUpdated(ts.toLocalDateTime());
        }
        return post;
    };

    private final JdbcOperations jdbcTemplate;

    @Autowired
    public PostJdbcDao(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void create(PostDto data) {
        jdbcTemplate.update(CREATE,
                data.getTitle(),
                data.getContent(),
                data.getDtCreated());
    }

    @Override
    public List<PostDto> getAll() {
        return jdbcTemplate.query(GET_ALL, rowMapper);
    }

    @Override
    public PostDto getById(long id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, rowMapper, id);
    }

    @Override
    public void update(long id, PostDto data) {
        jdbcTemplate.update(UPDATE,
                data.getTitle(),
                data.getContent(),
                data.getDtUpdated(),
                id);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }
}
