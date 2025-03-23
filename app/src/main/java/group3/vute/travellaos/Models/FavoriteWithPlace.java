package group3.vute.travellaos.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class FavoriteWithPlace {
    @Embedded
    public Favorite favorite;

    @Relation(
            parentColumn = "placeId",
            entityColumn = "id",
            entity = Place.class
    )
    public PlaceWithCategory placeWithCategory;

    public FavoriteWithPlace() {
    }

    public FavoriteWithPlace(Favorite favorite, PlaceWithCategory place) {
        this.favorite = favorite;
        this.placeWithCategory = place;
    }

    public Favorite getFavorite() {
        return favorite;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    public PlaceWithCategory getPlaceWithCategory() {
        return placeWithCategory;
    }

    public void setPlaceWithCategory(PlaceWithCategory place) {
        this.placeWithCategory = place;
    }
}
