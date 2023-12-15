package readradar.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import readradar.entity.User;
public interface UserDao extends JpaRepository<User, Long>{

}