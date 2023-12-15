package readradar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import readradar.controller.model.ReadRadarUser;
import readradar.service.ReadRadarService;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ReadRadarController{

    @Autowired
    private ReadRadarService readRadarService;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ReadRadarUser postUser(@RequestBody ReadRadarUser readRadarUser){
        log.info("Creating user {}", readRadarUser);
        return readRadarService.saveUser(readRadarUser);
    }
}