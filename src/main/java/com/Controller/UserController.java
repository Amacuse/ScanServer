package com.Controller;

import com.Bean.User;
import com.Service.Interface.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    public static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable() Long id) {
        LOGGER.debug("Receive 'GET' request with user id: " + id);
        User user = service.get(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postUser(@RequestBody User user, UriComponentsBuilder builder) {
        LOGGER.debug("Receive 'POST' request with request body: " + user);
        service.add(user);
        HttpHeaders headers = service.buildPath(builder, user.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Only for update
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putUser(@RequestBody User user, @PathVariable() Long id,
                                          UriComponentsBuilder builder) {
        LOGGER.debug("Receive 'PUT' request with user id: " + id + " and user: " + user);
        service.update(user, id);
        HttpHeaders headers = service.buildPath(builder, user.getId());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable() Long id) {
        LOGGER.debug("Receive 'DELETE' request with user id: " + id);
        service.remove(id);
    }
}
