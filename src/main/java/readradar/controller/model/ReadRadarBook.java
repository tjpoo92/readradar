package readradar.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import readradar.entity.Book;
import readradar.entity.Shelf;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ReadRadarBook {
    private Long bookId;
    private String bookName;
    private Integer isbn;
    private Integer numberOfPage;
    private Year yearPublished;
    private Boolean userCreated;
    private Set<ReadRadarShelf> shelves = new HashSet<>();

    public ReadRadarBook(Book book){
        this.bookId = book.getBookId();
        this.bookName = book.getBookName();
        this.isbn = book.getIsbn();
        this.numberOfPage = book.getNumberOfPages();
        this.yearPublished = book.getYearPublished();
        this.userCreated = book.getUserCreated();
        for(Shelf shelf : book.getShelves()){
            this.shelves.add(new ReadRadarShelf(shelf));
        }
  ;  }
}