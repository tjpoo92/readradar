package readradar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import readradar.controller.model.AuthorModel;
import readradar.controller.model.BookModel;
import readradar.controller.model.ShelfModel;
import readradar.controller.model.UserModel;
import readradar.dao.ShelfDao;
import readradar.dao.BookDao;
import readradar.dao.AuthorDao;
import readradar.dao.UserDao;
import readradar.entity.Author;
import readradar.entity.Book;
import readradar.entity.Shelf;
import readradar.entity.User;

import java.util.*;

@Service
public class ReadRadarService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private ShelfDao shelfDao;

    @Transactional
    public UserModel saveUser(UserModel userModel) {
        Long userId = userModel.getUserId();
        User user = findOrCreateUser(userId);
        copyUserFields(user, userModel);

        return new UserModel(userDao.save(user));
    }

    private void copyUserFields(User user, UserModel userModel) {
        user.setUserEmail(userModel.getUserEmail());
        user.setUserFirstName(userModel.getUserFirstName());
        user.setUserLastName(userModel.getUserLastName());
        user.setUserCreatedAt(userModel.getUserCreatedAt());
        user.setUserUpdatedAt(userModel.getUserUpdatedAt());
    }

    private User findOrCreateUser(Long userId) {
        User user;

        if (Objects.isNull(userId)){
            user = new User();
        } else{
            user = findUserById(userId);
        }
        return user;
    }

    private User findUserById(Long userId) {
        return userDao.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID:" + userId + " was not found."));
    }

    @Transactional(readOnly = true)
    public UserModel retrieveUserById(Long userId) {
        return new UserModel(findUserById(userId));
    }

    @Transactional
    public void deleteUserById(Long userId) {
        User user = findUserById(userId);
        userDao.delete(user);
    }

    @Transactional
    public AuthorModel saveAuthor(AuthorModel authorModel) {
        Long authorId = authorModel.getAuthorId();
        Author author = findOrCreateAuthor(authorId);
        copyAuthorFields(author, authorModel);

        return new AuthorModel(authorDao.save(author));
    }

    private void copyAuthorFields(Author author, AuthorModel authorModel) {
        author.setAuthorFirstName(authorModel.getAuthorFirstName());
        author.setAuthorLastName(authorModel.getAuthorLastName());
        author.setAuthorBirthDate(authorModel.getAuthorBirthDate());
    }

    private Author findOrCreateAuthor(Long authorId) {
        Author author;

        if (Objects.isNull(authorId)){
            author = new Author();
        } else{
            author = findAuthorById(authorId);
        }
        return author;

    }

    private Author findAuthorById(Long authorId) {
        return authorDao.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Author with ID:" + authorId + " was not found."));
    }

    @Transactional(readOnly = true)
    public List<AuthorModel> retrieveAllAuthors(Map<String,String> filters) {
        List<Author> authors;
        if (filters.isEmpty()){
            authors = authorDao.findAll();
        } else{
            if (filters.containsKey("authorFirstName") && filters.containsKey("authorLastName")){
                authors = authorDao.findByAuthorFirstNameAndAuthorLastName(filters.get("authorFirstName"), filters.get("authorLastName"));
            } else if (filters.containsKey("authorFirstName")) {
                authors = authorDao.findByAuthorFirstName(filters.get("authorFirstName"));
            } else{
                authors = authorDao.findByAuthorLastName(filters.get("authorLastName"));
            }
        }

        List<AuthorModel> result = new LinkedList<>();

        for (Author author : authors){
            AuthorModel authorModel = new AuthorModel(author);
            // TODO: Would prefer to show some, but not all books
            authorModel.getBooks().clear();
            result.add(authorModel);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public AuthorModel retrieveAuthorById(Long authorId) {
        return new AuthorModel(findAuthorById(authorId));
    }

    @Transactional(readOnly = true)
    public List<BookModel> retrieveAllBooks(Map<String, String> filters) {
        List<Book> books;
        if (filters.isEmpty()){
            books = bookDao.findAll();
        } else{
            books = bookDao.findByBookName(filters.get("bookName"));
        }

        List<BookModel> result = new LinkedList<>();

        for (Book book : books){
            BookModel bookModel = new BookModel(book);
            result.add(bookModel);
        }
        return result;
    }

    @Transactional
    public BookModel saveBook(Long AuthorId, BookModel bookModel){
        Long bookId = bookModel.getBookId();
        Long bookIsbn = bookModel.getIsbn();

        Book book = findOrCreateBook(bookId, bookIsbn);
        Author author = findOrCreateAuthor(AuthorId);

        if (book.getUserCreated()){
            copyBookFields(book, bookModel);
            book.setAuthor(author);
            author.getBooks().add(book);
            return new BookModel(bookDao.save(book));
        } else{
            return null;
        }
    }
    public BookModel saveBook(BookModel bookModel) {
        Long bookId = bookModel.getBookId();
        Long bookIsbn = bookModel.getIsbn();

        Book book = findOrCreateBook(bookId, bookIsbn);

        if (book.getUserCreated()){
            copyBookFields(book, bookModel);
            return new BookModel(bookDao.save(book));
        } else{
            return null;
        }
    }

    private void copyBookFields(Book book, BookModel bookModel) {
        book.setBookName(bookModel.getBookName());
        book.setNumberOfPages(bookModel.getNumberOfPages());
        book.setYearPublished(bookModel.getYearPublished());
    }

    private Book findOrCreateBook(Long bookId, Long bookIsbn) {
        Book book;

        if (Objects.isNull(bookId) && Objects.isNull(bookIsbn)){
            book = new Book();
            book.setUserCreated(true);
        } else if (Objects.isNull(bookIsbn)){
            book = findBookById(bookId);
        } else {
            book = findBookByIsbn(bookIsbn);
        }
        return book;
    }

    @Transactional(readOnly = true)
    private Book findBookByIsbn(Long bookIsbn) {
        return bookDao.findByIsbn(bookIsbn)
                .orElseThrow(()-> new NoSuchElementException
                        ("Book with ISBN:" + bookIsbn + " was not found"));
    }

    @Transactional(readOnly = true)
    private Book findBookById(Long bookId) {
        return bookDao.findById(bookId)
                .orElseThrow(()-> new NoSuchElementException
                        ("Book with ID:"+ bookId + " was not found."));
    }

    @Transactional(readOnly = true)
    public BookModel retrieveBookById(Long bookId) {
        return new BookModel(findBookById(bookId));
    }

    @Transactional(readOnly = true)
    public BookModel retrieveBookByIsbn(Long bookIsbn){
        return new BookModel(findBookByIsbn(bookIsbn));
    }

    @Transactional
    public void deleteBookById(Long bookId) {
        Book book = findBookById(bookId);
        if (book.getUserCreated()){
            for (Shelf shelf : book.getShelves()) {
                book.removeShelf(shelf);
            }
            bookDao.delete(book);
        } else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unable to delete non-user created book");
        }
    }

    @Transactional
    public ShelfModel saveShelf(Long userId, ShelfModel shelfModel) {
        Long shelfId = shelfModel.getShelfId();
        Shelf shelf = findOrCreateShelf(shelfId);
        User user = findOrCreateUser(userId);

        copyShelfFields(shelf, shelfModel);

        shelf.setUser(user);
        user.getShelves().add(shelf);

        return new ShelfModel(shelfDao.save(shelf));
    }

    private void copyShelfFields(Shelf shelf, ShelfModel shelfModel) {
        shelf.setShelfName(shelfModel.getShelfName());
    }

    public ShelfModel addBookToShelf(Long shelfId, Long bookId) {
        Shelf shelf = findOrCreateShelf(shelfId);
        Book book = findOrCreateBook(bookId, null);

        shelf.getBooks().add(book);
        book.getShelves().add(shelf);

        Shelf dbShelf = shelfDao.save(shelf);
        return new ShelfModel(dbShelf);
    }

    private Shelf findOrCreateShelf(Long shelfId) {
        Shelf shelf;

        if(Objects.isNull(shelfId)){
            shelf = new Shelf();
        } else{
            shelf = findShelfById(shelfId);
        }
        return shelf;
    }

    private Shelf findShelfById(Long shelfId) {
        return shelfDao.findById(shelfId)
                .orElseThrow(()->new NoSuchElementException
                        ("Shelf with ID:" + shelfId + " was not found."));
    }

    @Transactional(readOnly = true)
    public List<ShelfModel> retrieveAllShelves(Long userId) {
        List<Shelf> shelves = shelfDao.findByUserId(userId);
        List<ShelfModel> result = new LinkedList<>();
        for (Shelf shelf : shelves){
            ShelfModel shelfModel = new ShelfModel(shelf);
            result.add(shelfModel);
        }
        return result;
    }

    @Transactional
    public void deleteShelfById(Long shelfId) {
        Shelf shelf = findShelfById(shelfId);
        shelfDao.delete(shelf);
    }


}