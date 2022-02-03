package ru.specialist.spring.dao.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.specialist.spring.entity.Users;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

     Users findBylastName(String lastName);

     @Query(value = """
             select * from users u  where u.is_active=false             
             """, nativeQuery = true)
     List<Users> findDisabledUsers();

}
