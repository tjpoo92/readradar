package readradar.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import readradar.entity.Shelf;

import java.util.Date;

@Data
@NoArgsConstructor
public class ShelfModel {
    private Long shelfId;
    private Date shelfCreatedAt;
    private Date shelfUpdatedAt;

    public ShelfModel(Shelf shelf){
        this.shelfId = shelf.getShelfId();
        this.shelfCreatedAt = shelf.getShelfCreatedAt();
        this.shelfUpdatedAt = shelf.getShelfUpdatedAt();
    }
}