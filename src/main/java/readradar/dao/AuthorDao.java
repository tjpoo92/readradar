package readradar.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import readradar.entity.Author;

import java.util.List;

public interface AuthorDao extends JpaRepository<Author, Long> {
    List<Author> findByAuthorLastName(String author_last_name);
    List<Author> findByAuthorFirstName(String author_first_name);
    List<Author> findByAuthorFirstNameAndAuthorLastName(String author_first_name, String author_last_name);

}