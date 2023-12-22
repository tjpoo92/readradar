package readradar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import readradar.controller.model.AuthorModel;
import readradar.controller.model.BookModel;
import readradar.controller.model.ShelfModel;
import readradar.controller.model.UserModel;

import readradar.service.ReadRadarService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public UserModel getUserByUserId(@PathVariable Long userId){
        log.info("Retrieving user using ID: {}", userId);
        return readRadarService.retrieveUserById(userId);
    }

    @PutMapping("/users/{userId}")
    public UserModel putUser(@RequestBody UserModel userModel, @PathVariable Long userId){
        userModel.setUserId(userId);
        log.info("Update User {} with {}", userId, userModel);
        return readRadarService.saveUser(userModel);
    }

    @DeleteMapping("/users/{userId}")
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
    public List<AuthorModel> getAllAuthors(@RequestParam(required = false) Map<String,String> filters){
        log.info("Retrieving all authors with the following filters {}", filters);
        return readRadarService.retrieveAllAuthors(filters);
    }

    @GetMapping("/authors/{authorId}")
    public AuthorModel getAuthorById(@PathVariable Long authorId){
        log.info("Retrieving author using ID: {}", authorId);
        return readRadarService.retrieveAuthorById(authorId);
    }

    // Book Endpoints
    @PostMapping("/authors/{authorId}/books")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookModel postBook(@PathVariable Long authorId, @RequestBody BookModel bookModel){
        log.info("Attempting to create book {} associated with author ID: {}", bookModel, authorId);
        return readRadarService.saveBook(authorId, bookModel);
    }

    @GetMapping("/books")
    public List<BookModel> getAllBook(@RequestParam(required = false) Map<String, String> filters){
        log.info("Retrieving all books with the following filters {}", filters);
        return readRadarService.retrieveAllBooks(filters);
    }

    @GetMapping("/books/{bookId}")
    public BookModel getBookById(@PathVariable Long bookId){
        log.info("Retrieving book using ID: {}", bookId);
        return readRadarService.retrieveBookById(bookId);
    }

    @GetMapping("/books/isbn/{bookIsbn}")
    public BookModel getBookByIsbn(@PathVariable Long bookIsbn){
        log.info("Retrieving book using ISBN: {}", bookIsbn);
        return readRadarService.retrieveBookByIsbn(bookIsbn);
    }

    @PutMapping("/books/{bookId}")
    public BookModel putBook(@PathVariable Long bookId, @RequestBody BookModel bookModel){
        bookModel.setBookId(bookId);
        log.info("Update Book {} with {}", bookId, bookModel);
        BookModel updatedBook = readRadarService.saveBook(bookModel);
        if (Objects.isNull(updatedBook)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unable to update non-user created book");
        } else {
            return updatedBook;
        }
    }

    @DeleteMapping("/books/{bookId}")
    public Map<String, String> deleteBookById(@PathVariable Long bookId){
        log.info("Attempting to delete book with ID: {}", bookId);
        readRadarService.deleteBookById(bookId);
        return Map.of("message", "Deletion of user with ID:" + bookId);
    }

    // Shelf Endpoints
    @PostMapping("/users/{userId}/shelves")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ShelfModel postShelf(@PathVariable Long userId, @RequestBody ShelfModel shelfModel){
        log.info("Attempting to create shelf {} associated with user ID: {}", shelfModel, userId);
        return readRadarService.saveShelf(userId, shelfModel);
    }

    @GetMapping("/users/{userId}/shelves")
    public List<ShelfModel> getAllShelves(@PathVariable Long userId){
        log.info("Retrieving all shelves");
        return readRadarService.retrieveAllShelves(userId);
    }

    @PutMapping("/books/{bookId}/shelves/{shelfId}")
    public ShelfModel putShelf(@PathVariable Long shelfId, @PathVariable Long bookId){
        log.info("Attempting to update shelf ID: {} with book ID: {}", shelfId, bookId);
        return readRadarService.addBookToShelf(shelfId,bookId);
    }

    @DeleteMapping("/shelves/{shelfId}")
    public Map<String,String> deleteShelfById(@PathVariable Long shelfId){
        log.info("Attempting to delete shelf with ID: {}",shelfId);
        readRadarService.deleteShelfById(shelfId);
        return Map.of("message", "Deletion of shelf with ID:" + shelfId + " completed.");
    }

}