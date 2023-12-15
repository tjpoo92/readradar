package readradar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import readradar.controller.model.ReadRadarUser;
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
    public ReadRadarUser saveUser(ReadRadarUser readRadarUser) {
        Long userId = readRadarUser.getUserId();
        User user = findOrCreateUser(userId);
        copyUserFields(user, readRadarUser);

        return new ReadRadarUser(userDao.save(user));
    }

    private void copyUserFields(User user, ReadRadarUser readRadarUser) {
        user.setUserEmail(readRadarUser.getUserEmail());
        user.setUserFirstName(readRadarUser.getUserFirstName());
        user.setUserLastName(readRadarUser.getUserLastName());
        user.setUserCreatedAt(readRadarUser.getUserCreatedAt());
        user.setUserUpdatedAt(readRadarUser.getUserUpdatedAt());
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

}