package readradar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import readradar.controller.model.AuthorModel;
import readradar.controller.model.UserModel;
import readradar.dao.ShelfDao;
import readradar.dao.BookDao;
import readradar.dao.AuthorDao;
import readradar.dao.UserDao;
import readradar.entity.Author;
import readradar.entity.User;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
        // TODO: Author information many users, many books
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
        // TODO: Fix birthdate, dates in general prob need fixed
        // TODO: Books one author, many books
        // TODO: Users many authors, many users
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
    public List<AuthorModel> retrieveAllAuthors() {
        List<Author> authors = authorDao.findAll();
        List<AuthorModel> result = new LinkedList<>();

        for (Author author : authors){
            AuthorModel authorModel = new AuthorModel(author);
            // TODO: Verify once book endpoints are done
            authorModel.getBooks().stream().limit(5);
            result.add(authorModel);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public AuthorModel retrieveAuthorById(Long authorId) {
        return new AuthorModel(findAuthorById(authorId));
    }
}