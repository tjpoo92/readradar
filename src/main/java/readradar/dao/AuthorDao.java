package readradar.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import readradar.entity.Author;

public interface AuthorDao extends JpaRepository<Author, Long> {
}