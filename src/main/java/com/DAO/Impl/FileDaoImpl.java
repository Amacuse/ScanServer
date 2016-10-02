package com.DAO.Impl;

import com.Bean.File;
import com.Bean.FileContent;
import com.Bean.User;
import com.DAO.Interface.FileDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.Set;

@Repository
@Transactional()
public class FileDaoImpl extends AbstractDAO<File> implements FileDao {

    public static final Logger LOGGER = LogManager.getLogger(FileDaoImpl.class);


    @PostConstruct
    public void init() {
        setClazz(File.class);
    }

    @Override
    public void add(File file) {

        LOGGER.debug("Adding file: name: " + file.getName()
                + ", user: " + file.getUser().getId());

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<File> query = cb.createQuery(File.class);
        Root<File> root = query.from(File.class);

        Predicate conditionOne = cb.equal(root.get("name"), file.getName());
        Predicate conditionTwo = cb.equal(root.get("user"), file.getUser());

        query.where(cb.and(conditionOne, conditionTwo));

        File result = null;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            /*NOP*/
        }

        if (result == null) {
            em.persist(file);
        } else {
            for (FileContent fileContent : file.getFileContents()) {
                fileContent.setFile(result);
                em.persist(fileContent);
            }
            file.setId(result.getId());
        }
    }

    @Override
    public Set<File> getAll(Long user_id) {
        LOGGER.debug("Trying to retrieve all the files of user with id: " + user_id);
        User user = em.find(User.class, user_id);
        if (user == null) {
            //user with such id does not exist in DB
            return Collections.<File>emptySet();
        }
        Hibernate.initialize(user.getFiles());
        LOGGER.debug("User has the collection of files that has a size: " + user.getFiles().size());
        return user.getFiles();
    }
}
