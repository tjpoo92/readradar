package readradar.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import readradar.entity.Book;
public interface BookDao extends JpaRepository<Book, Long> {

}