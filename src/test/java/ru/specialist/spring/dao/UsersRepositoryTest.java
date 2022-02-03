package ru.specialist.spring.dao;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.specialist.spring.config.DataConfig;
import ru.specialist.spring.dao.repository.UsersRepository;
import ru.specialist.spring.entity.Users;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataConfig.class)
@Sql(scripts = "classpath:schema.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional

public class UsersRepositoryTest {

    private final UsersRepository usersRep;

    @Autowired
    public UsersRepositoryTest(UsersRepository usersRep) {
        this.usersRep = usersRep;
    }


    @Test
    void create(){
        Users Users = new Users();
        Users.setUsername("newUsername");
        Users.setFirstName("FirstName");
        Users.setCreatedAt(LocalDateTime.now());

        usersRep.save(Users);

        assertEquals("newUsername", usersRep.getById(5L).getUsername());
    }

    @Test
    void update(){
        Users Users = usersRep.getById(1L);
        Users.setUsername("NewSuperUser");

        usersRep.save(Users);
        assertEquals("NewSuperUser", usersRep.getById(1L).getUsername());
    }

    @Test
    void delete() {
        usersRep.deleteById(1L);
        assertEquals(3,usersRep.count());
    }

    @Test
    void UsersRoles(){
        Users Users = usersRep.getById(1L);
//        System.out.println(Users.getRolesList().get(0).getRoleName().toString());
//        System.out.println(Users.getRolesList().get(1).getRoleName().toString());
        assertEquals(2, Users.getRolesList().size());
        Users = usersRep.getById(2L);
        assertEquals(1, Users.getRolesList().size());
    }

    @Test
    void FindBylastName(){
        Users Users = usersRep.findBylastName("Ptushkin");
//        System.out.println(Users.getUsername());
        assertEquals("alex", Users.getUsername());
    }

    @Test
    void FindTerminatedUsers(){
        List<Users> users= usersRep.findDisabledUsers();
//        System.out.println(users.get(0).getUsername());
        assertEquals(1, users.size());
    }

}
