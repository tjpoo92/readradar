package readradar.dao;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import readradar.entity.Book;
import readradar.entity.Shelf;

import java.util.List;
import java.util.Optional;

public interface BookDao extends JpaRepository<Book, Long> {

    List<Book> findByBookName(String book_name);

    Optional<Book> findByIsbn(Long bookIsbn);

    @Query(value = "select * from book b where b.author_id = :authorId order by year_published desc", nativeQuery = true)
    List<Book> findByAuthorId(@Param("authorId") Long authorId, Pageable pageable);
}