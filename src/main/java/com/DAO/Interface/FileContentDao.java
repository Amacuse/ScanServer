package com.DAO.Interface;

import com.Bean.DTO.FileDTO;
import com.Bean.FileContent;

import java.util.List;
import java.util.Set;

public interface FileContentDao extends Dao<FileContent> {

    List<FileDTO> getAllFileContentCreationDate(Long file_id);

    List<FileContent> getAllChosen(Set<Long> setID);

    void removeAllChosen(Set<Long> setID);

    Set<FileContent> getAll(Long file_id);
}
