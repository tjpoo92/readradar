package readradar.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelfId;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date shelfCreatedAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date shelfUpdatedAt;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_shelf",
            joinColumns = @JoinColumn(name="shelf_id"),
            inverseJoinColumns = @JoinColumn(name="book_id"))
    private Set<Book> books = new HashSet<>();

}