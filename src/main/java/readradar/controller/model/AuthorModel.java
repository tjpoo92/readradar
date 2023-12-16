package readradar.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import readradar.entity.Author;
import readradar.entity.Book;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class AuthorModel {
    private Long authorId;
    private String authorFirstName;
    private String authorLastName;
    private Date authorBirthDate;
    private Set<BookModel> books = new HashSet<>();

    public AuthorModel(Author author){
        this.authorId = author.getAuthorId();
        this.authorFirstName = author.getAuthorFirstName();
        this.authorLastName = author.getAuthorLastName();
        this.authorBirthDate = author.getAuthorBirthDate();
        for (Book book : author.getBooks()){
            this.books.add(new BookModel(book));
        }
    }
}