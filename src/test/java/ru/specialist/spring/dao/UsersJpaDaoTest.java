package ru.specialist.spring.dao;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.specialist.spring.config.DataConfig;
import ru.specialist.spring.dao.jpa.UserJPADao;
import ru.specialist.spring.entity.*;
import ru.specialist.spring.entity.Users;
import ru.specialist.spring.entity.Users;
import ru.specialist.spring.entity.Users;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataConfig.class)
@Sql(scripts = "classpath:schema.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional

public class UsersJpaDaoTest {

    private final AbstractDAO<Users> UsersDao;

    @Autowired
    public UsersJpaDaoTest(AbstractDAO<Users> usersDao) {
        this.UsersDao = usersDao;
    }

    @Test
    void create(){
        Users Users = new Users();
        Users.setUsername("newUsername");
        Users.setFirstName("FirstName");
        Users.setCreatedAt(LocalDateTime.now());

        UsersDao.create(Users);

        assertEquals("newUsername", UsersDao.getById(5).getUsername());
    }

    @Test
    void update(){
        Users Users = UsersDao.getById(1);
        Users.setUsername("NewSuperUser");

        UsersDao.update(1, Users);
        assertEquals("NewSuperUser", UsersDao.getById(1).getUsername());
    }

    @Test
    void UsersRoles(){
        Users Users = UsersDao.getById(1);
//        System.out.println(Users.getRolesList().get(0).getRoleName().toString());
//        System.out.println(Users.getRolesList().get(1).getRoleName().toString());
        assertEquals(2, Users.getRolesList().size());
        Users = UsersDao.getById(2);
        assertEquals(1, Users.getRolesList().size());
    }


}
