package readradar.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import readradar.entity.Shelf;

import java.util.List;

public interface ShelfDao extends JpaRepository<Shelf,Long> {

    @Query(value = "select * from shelf s where s.user_id = :userId", nativeQuery = true)
    List<Shelf> findByUserId(@Param("userId") Long userId);
}