package group3.vute.travellaos.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PlaceWithCategory {
    @Embedded
    public Place place;

    @Relation(
            parentColumn = "categoryId",
            entityColumn = "id"
    )
    public PlaceCategory category;

    public PlaceCategory getCategory() {
        return category;
    }

    public void setCategory(PlaceCategory category) {
        this.category = category;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
