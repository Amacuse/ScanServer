package com.Service.Interface;

import com.Bean.DTO.FileDTO;

import java.util.List;
import java.util.Set;

public interface FileContentService extends HeaderService,Service<FileDTO> {
    List<FileDTO> getAllFileContentCreationDate(Long file_id);

    List<FileDTO> getAllChosen(Set<Long> setID);

    void removeAllChosen(Set<Long> setID);
}
