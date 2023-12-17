package readradar.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import readradar.controller.model.AuthorModel;
import readradar.controller.model.BookModel;
import readradar.controller.model.UserModel;
import readradar.service.ReadRadarService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ReadRadarController{

    @Autowired
    private ReadRadarService readRadarService;

    // User Endpoints
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

    // Author Endpoints
    @PostMapping("/authors")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AuthorModel postAuthor(@RequestBody AuthorModel authorModel){
        log.info("Creating author {}", authorModel);
        return readRadarService.saveAuthor(authorModel);
    }

    @GetMapping("/authors")
    @ResponseStatus(code = HttpStatus.OK)
    public List<AuthorModel> getAllAuthor(@RequestParam(required = false) Map<String,String> filters){
        log.info("Retrieving all authors with the following filters {}", filters);
        return readRadarService.retrieveAllAuthors(filters);
    }

    @GetMapping("/authors/{authorId}")
    @ResponseStatus(code = HttpStatus.OK)
    public AuthorModel getAuthorById(@PathVariable Long authorId){
        log.info("Retrieving author user ID: {}", authorId);
        return readRadarService.retrieveAuthorById(authorId);
    }

    // Book Endpoints
    @PostMapping("/books")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookModel postBook(@RequestBody BookModel bookModel){
        log.info("Creating book {}", bookModel);
        return readRadarService.saveBook(bookModel);
    }

    @GetMapping("/books")
    @ResponseStatus(code = HttpStatus.OK)
    public List<BookModel> getAllBook(@RequestParam(required = false) Map<String, String> filters){
        log.info("Retrieving all books with the following filters {}", filters);
        return readRadarService.retrieveAllBooks(filters);
    }

}