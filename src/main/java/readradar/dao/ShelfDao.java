package readradar.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import readradar.entity.Shelf;

import java.util.List;

public interface ShelfDao extends JpaRepository<Shelf,Long> {
    List<Shelf> findByUserId(Long user_id);
}