package readradar.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import readradar.entity.Author;
import readradar.entity.Shelf;
import readradar.entity.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ReadRadarUser{
    private Long userId;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private Date userCreatedAt;
    private Date userUpdatedAt;
    private Set<ReadRadarAuthor> authors = new HashSet<>();
    private Set<ReadRadarShelf> shelves = new HashSet<>();

    public ReadRadarUser(User user) {
        this.userId = user.getUserId();
        this.userEmail = user.getUserEmail();
        this.userFirstName = user.getUserFirstName();
        this.userLastName = user.getUserLastName();
        this.userCreatedAt = user.getUserCreatedAt();
        this.userUpdatedAt = user.getUserUpdatedAt();
        for (Author author : user.getAuthors()){
            this.authors.add(new ReadRadarAuthor(author));
        }
        for (Shelf shelf : user.getShelves()){
            this.shelves.add(new ReadRadarShelf(shelf));
        }
    }
}