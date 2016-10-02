package com.Controller;

import com.Bean.DTO.FileDTO;
import com.Service.Interface.FileContentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/user/{user_id}/file/{file_id}/content")
public class FileContentController {

    public static final Logger LOGGER = LogManager.getLogger(FileContentController.class);

    @Autowired
    private FileContentService service;

    @RequestMapping(value = "/{content_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<FileDTO> getFileContent(@PathVariable("content_id") Long content_id,
                                                   @PathVariable() Long user_id) {
        LOGGER.debug("Receive 'GET' request with file content id: " + content_id);
        FileDTO date = service.get(content_id);
        return new ResponseEntity<>(date, HttpStatus.OK);
    }

    @RequestMapping(value = "/{content_id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    private void deleteFileContent(@PathVariable("content_id") Long content_id,
                                   @PathVariable() Long user_id) {
        LOGGER.debug("Receive 'DELETE' request with file content id: " + content_id);
        service.remove(content_id);
    }

    @RequestMapping(value = "/date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<FileDTO>> getFileSavedContentDate(@PathVariable() Long file_id,
                                                                  @PathVariable() Long user_id) {
        LOGGER.debug("Receive 'GET' request for the id and date of file content with file id: " + file_id);
        List<FileDTO> date = service.getAllFileContentCreationDate(file_id);
        return new ResponseEntity<>(date, HttpStatus.OK);
    }

    @RequestMapping(value = "/chosen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<FileDTO>> getChosenFileContent(@RequestBody Set<Long> setID,
                                                               @PathVariable() Long user_id) {
        LOGGER.debug("Receive 'GET' request for file contents with given set of id");
        List<FileDTO> allChosen = service.getAllChosen(setID);
        return new ResponseEntity<>(allChosen, HttpStatus.OK);
    }

    @RequestMapping(value = "/delAll", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    private void deleteAllChosen(@RequestBody Set<Long> setID, @PathVariable() Long user_id) {
        LOGGER.debug("Receive 'DELETE' request for file contents with given set of id");
        service.removeAllChosen(setID);
    }
}
