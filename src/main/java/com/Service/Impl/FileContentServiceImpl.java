package com.Service.Impl;

import com.Bean.DTO.FileDTO;
import com.Bean.FileContent;
import com.DAO.Interface.FileContentDao;
import com.Service.Interface.FileContentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FileContentServiceImpl extends AbstractService<FileContent> implements FileContentService {

    public static final Logger LOGGER = LogManager.getLogger(FileContentServiceImpl.class);
    private static final String PATH = "/BackUpFiles/user/{user_id}/file/{file_id}/content{content_id}";

    @Autowired
    private FileContentDao dao;

    @Override
    public FileDTO get(Long content_id) {
        FileContent content = dao.getByID(content_id);
        FileDTO fileDTO = new FileDTO();
        fileDTO.setContent(content.getContent());
        fileDTO.setBackUpDate(content.getCreation());
        isValid(content, content_id);
        return fileDTO;
    }

    @Override
    public void add(FileDTO fileDTO) {

    }

    @Override
    public void update(FileDTO fileDTO, Long id) {

    }

    @Override
    public void remove(Long content_id) {
        FileContent content = dao.getByID(content_id);
        isValid(content, content_id);
        dao.remove(content);
    }

    @Override
    public List<FileDTO> getAllFileContentCreationDate(Long file_id) {
        return dao.getAllFileContentCreationDate(file_id);
    }

    @Override
    public List<FileDTO> getAllChosen(Set<Long> setID) {
        List<FileContent> chosen = dao.getAllChosen(setID);
        List<FileDTO> fileDTOList = new ArrayList<>();
        for (FileContent fileContent : chosen) {
            FileDTO dto = new FileDTO();
            dto.setContent(fileContent.getContent());
            dto.setBackUpDate(fileContent.getCreation());

            fileDTOList.add(dto);
        }
        return fileDTOList;
    }

    @Override
    public void removeAllChosen(Set<Long> setID) {
        dao.removeAllChosen(setID);
    }

    @Override
    protected String setPath() {
        return PATH;
    }
}
