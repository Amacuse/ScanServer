package com.DAO.Impl;

import com.Bean.User;
import com.DAO.Interface.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;

@Repository
@Transactional()
public class UserDaoImpl extends AbstractDAO<User> implements UserDao {

    @PostConstruct
    public void init() {
        setClazz(User.class);
    }

    @Override
    public void add(User user) throws EntityExistsException {
        user.addFiles();
        super.add(user);
    }
}
