package com.Service.Impl;

import com.Bean.User;
import com.DAO.Interface.UserDao;
import com.Exception.ConstraintViolationException;
import com.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    private UserDao dao;

    private static final String PATH = "/UserService/user/{id}";

    @Override
    protected String setPath() {
        return PATH;
    }

    @Override
    public User get(Long id) {
        User user = dao.getByID(id);
        isValid(user, id);
        return user;
    }

    @Override
    public void add(User user) {
        try {
            dao.add(user);
        } catch (PersistenceException e) {
            throw new ConstraintViolationException(
                    ms.getMessage("user.exception.emailExist", new Object[]{user.getEmail()}, getLocale()));
        }
    }

    @Override
    public void update(User user, Long id) {
        User userFromDB = dao.getByID(id);
        isValid(userFromDB, id);
        if (user.getId() == null) {
            user.setId(id); //if user in request body doesn't have an ID
        }
        try {
            dao.update(user);
        } catch (Exception e) { //caller tries to change field which has a unique constraint and presents in DB
            throw new ConstraintViolationException(
                    ms.getMessage("user.exception.emailExist", new Object[]{user.getEmail()}, getLocale()));
        }
    }

    @Override
    public void remove(Long id) {
        User user = dao.getByID(id);
        isValid(user, id);
        dao.remove(user);
    }
}
