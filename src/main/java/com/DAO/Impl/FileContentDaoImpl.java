package com.DAO.Impl;

import com.Bean.DTO.FileDTO;
import com.Bean.File;
import com.Bean.FileContent;
import com.DAO.Interface.FileContentDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
@Transactional()
public class FileContentDaoImpl extends AbstractDAO<FileContent> implements FileContentDao {

    public static final Logger LOGGER = LogManager.getLogger(FileContentDaoImpl.class);

    @PostConstruct
    public void init() {
        setClazz(FileContent.class);
    }

    @Override
    public List<FileDTO> getAllFileContentCreationDate(Long file_id) {
        LOGGER.debug("Trying to retrieve all the content creation dates of file with id: " + file_id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FileDTO> query = cb.createQuery(FileDTO.class);
        Root<FileContent> root = query.from(FileContent.class);

        Predicate predicate = cb.equal(root.get("file"), file_id);

        //construct FileDTO with only two fields: id and creation
        query.select(cb.construct(FileDTO.class,
                root.get("id"),
                root.get("creation"))).
                where(predicate);

        List<FileDTO> resultList = em.createQuery(query).getResultList();
        LOGGER.debug("Result list has a size: " + resultList.size());
        return resultList;
    }

    @Override
    public List<FileContent> getAllChosen(Set<Long> setID) {
        if (setID == null || setID.isEmpty()) {
            return Collections.<FileContent>emptyList();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FileContent> query = cb.createQuery(FileContent.class);
        Root<FileContent> root = query.from(FileContent.class);
        query.where(root.get("id").in(setID));
        return em.createQuery(query).getResultList();
    }

    @Override
    public void removeAllChosen(Set<Long> setID) {
        if (setID == null || setID.isEmpty()) {
            return;
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<FileContent> query = cb.createCriteriaDelete(FileContent.class);
        Root<FileContent> root = query.from(FileContent.class);

        query.where(root.get("id").in(setID));
        em.createQuery(query).executeUpdate();
    }

    @Override
    public Set<FileContent> getAll(Long file_id) {
        File file = em.find(File.class, file_id);
        if (file == null) {
            return Collections.<FileContent>emptySet();
        }
        Hibernate.initialize(file.getFileContents());
        return file.getFileContents();
    }

}
