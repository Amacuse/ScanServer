package com.Controller;

import com.Bean.DTO.FileDTO;
import com.Service.Interface.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@RequestMapping(value = "/user/{user_id}/file")
public class FileController {

    public static final Logger LOGGER = LogManager.getLogger(FileController.class);

    @Autowired
    private FileService service;

    @RequestMapping(method = RequestMethod.GET)
    private ResponseEntity<Set<FileDTO>> getFiles(@PathVariable() Long user_id) {
        LOGGER.debug("Receive 'GET' request for files with user id: " + user_id);
        Set<FileDTO> allFiles = service.getAll(user_id);
        return new ResponseEntity<>(allFiles, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> postFile(@RequestBody FileDTO fileDTO, @PathVariable() Long user_id,
                                           UriComponentsBuilder builder) {
        LOGGER.debug("Receive 'POST' request with user id: " + user_id + " and fileDTO: " + fileDTO);
        service.add(fileDTO, user_id);
        HttpHeaders headers = service.buildPath(builder, user_id, fileDTO.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{file_id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteFile(@PathVariable() Long file_id) {
        LOGGER.debug("Receive 'DELETE' request with file id: " + file_id);
        service.remove(file_id);
    }
}
