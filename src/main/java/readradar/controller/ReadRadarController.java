package readradar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import readradar.controller.model.UserModel;
import readradar.entity.User;
import readradar.service.ReadRadarService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ReadRadarController{

    @Autowired
    private ReadRadarService readRadarService;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserModel postUser(@RequestBody UserModel userModel){
        log.info("Creating user {}", userModel);
        return readRadarService.saveUser(userModel);
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserModel getUserByUserId(@PathVariable Long userId){
        log.info("Retrieving user using ID: {}", userId);
        return readRadarService.retrieveUserById(userId);
    }

    @PutMapping("/users/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserModel putUser(@RequestBody UserModel userModel, @PathVariable Long userId){
        userModel.setUserId(userId);
        log.info("Update User {} with {}", userId, userModel);
        return readRadarService.saveUser(userModel);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, String> deleteUserById(@PathVariable Long userId){
        log.info("Deleting User with ID: {}", userId);
        readRadarService.deleteUserById(userId);
        return Map.of("message", "Deletion of user with ID:" + userId);
    }


}