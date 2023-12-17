package readradar.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import readradar.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao extends JpaRepository<Book, Long> {
    List<Book> findByBookName(String book_name);

    Optional<Book> findByIsbn(Long bookIsbn);
}