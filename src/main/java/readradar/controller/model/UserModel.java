package readradar.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import readradar.entity.Author;
import readradar.entity.Shelf;
import readradar.entity.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserModel {
    private Long userId;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private Set<AuthorModel> authors = new HashSet<>();
    private Set<ShelfModel> shelves = new HashSet<>();

    public UserModel(@NotNull User user) {
        this.userId = user.getUserId();
        this.userEmail = user.getUserEmail();
        this.userFirstName = user.getUserFirstName();
        this.userLastName = user.getUserLastName();
        for (Author author : user.getAuthors()){
            this.authors.add(new AuthorModel(author));
        }
        for (Shelf shelf : user.getShelves()){
            this.shelves.add(new ShelfModel(shelf));
        }
    }
}