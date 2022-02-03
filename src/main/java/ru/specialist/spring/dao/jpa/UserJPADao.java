package ru.specialist.spring.dao.jpa;

import org.springframework.stereotype.Repository;
import ru.specialist.spring.dao.AbstractDAO;
import ru.specialist.spring.entity.Users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserJPADao implements AbstractDAO<Users> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Users data) {
        em.persist(data);
    }


    @Override
    public List<Users> getAll() {
        //JPQL
        String query = "select p from Users p";
        return em.createQuery(query, Users.class).getResultList();
    }

    @Override
    public Users getById(long id) {
        return em.find(Users.class, id);
    }

    @Override
    public void update(long id, Users data) {
        data.setUserId(id);
        em.merge(data);
    }

    @Override
    public void delete(long id) {
        em.remove(getById(id));
    }


}
