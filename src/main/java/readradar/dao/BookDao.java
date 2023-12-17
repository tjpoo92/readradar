package readradar.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import readradar.entity.Book;

import java.util.List;

public interface BookDao extends JpaRepository<Book, Long> {
    List<Book> findByBookName(String book_name);

}