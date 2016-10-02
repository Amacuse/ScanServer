package com.Service.Interface;

import com.Bean.DTO.FileDTO;

import java.util.Set;

public interface FileService extends HeaderService {

    void add(FileDTO fileDTO, Long user_id);

    void remove(Long file_id);

    Set<FileDTO> getAll(Long user_id);
}
