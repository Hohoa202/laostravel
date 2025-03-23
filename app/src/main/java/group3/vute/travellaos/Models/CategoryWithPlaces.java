package group3.vute.travellaos.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithPlaces {
    @Relation(
            parentColumn = "id",
            entityColumn = "categoryId"
    )
    public List<Place> place;

    @Embedded
    public PlaceCategory category;

    public List<Place> getPlace() {
        return place;
    }

    public void setPlace(List<Place> place) {
        this.place = place;
    }

    public PlaceCategory getCategory() {
        return category;
    }

    public void setCategory(PlaceCategory category) {
        this.category = category;
    }
}
