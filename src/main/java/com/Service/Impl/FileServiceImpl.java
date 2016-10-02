package com.Service.Impl;

import com.Bean.DTO.FileDTO;
import com.Bean.File;
import com.Bean.FileContent;
import com.Bean.User;
import com.DAO.Interface.FileDao;
import com.Exception.ResourceNotFoundException;
import com.Service.Interface.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class FileServiceImpl extends AbstractService<File> implements FileService {

    public static final Logger LOGGER = LogManager.getLogger(FileServiceImpl.class);
    private static final String PATH = "/BackUpFiles/user/{user_id}/file/{file_id}";

    @Autowired
    private FileDao dao;


    @Override
    protected String setPath() {
        return PATH;
    }

    @Override
    public void add(FileDTO fileDTO, Long user_id) {
        File file = new File(fileDTO.getFileName());
        FileContent fileContent = new FileContent(fileDTO.getContent());
        file.getFileContents().add(fileContent);
        fileContent.setFile(file);

        setUserId(file, user_id);

        //catch exception if user does not exist
        try {
            dao.add(file);
        } catch (DataIntegrityViolationException e) {
            LOGGER.debug(e.getMessage());
            throw new ResourceNotFoundException(
                    ms.getMessage("exception.resourceNotFound", new Object[]{user_id}, getLocale()));
        }
        //set id in order to build 'Location header'
        fileDTO.setId(file.getId());
    }

    @Override
    public void remove(Long file_id) {
        LOGGER.debug("File id for deletion is: " + file_id);
        File file = dao.getByID(file_id);
        LOGGER.debug("File with id: " + file_id + " exist? ---> " + (file != null));
        isValid(file, file_id);
        dao.remove(file);
    }

    @Override
    public Set<FileDTO> getAll(Long user_id) {
        Set<File> fileSet = dao.getAll(user_id);
        if (fileSet.isEmpty()) {
            return Collections.<FileDTO>emptySet();
        }
        Set<FileDTO> fileDTOs = new HashSet<>();
        for (File file : fileSet) {
            fileDTOs.add(new FileDTO(file.getId(), file.getName()));
        }
        return fileDTOs;
    }

    private void setUserId(File file, Long user_id) {
        User user = new User();
        user.setId(user_id);
        file.setUser(user);
    }
}
