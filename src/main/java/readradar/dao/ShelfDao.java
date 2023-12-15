package readradar.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import readradar.entity.Shelf;

public interface ShelfDao extends JpaRepository<Shelf,Long> {
}