package readradar.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String bookName;
    @Column(unique = true)
    private Long isbn;
    private Integer numberOfPages;
    private Year yearPublished;
    private Boolean userCreated;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private Author author;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
    private Set<Shelf> shelves = new HashSet<>();

    public void addShelf(Shelf shelf){
        this.shelves.add(shelf);
        shelf.getBooks().add(this);
    }

    public void removeShelf(Shelf shelf){
        this.shelves.remove(shelf);
        shelf.getBooks().remove(this);
    }

}