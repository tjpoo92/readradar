package readradar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import readradar.controller.model.UserModel;
import readradar.dao.ShelfDao;
import readradar.dao.BookDao;
import readradar.dao.AuthorDao;
import readradar.dao.UserDao;
import readradar.entity.User;

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
}