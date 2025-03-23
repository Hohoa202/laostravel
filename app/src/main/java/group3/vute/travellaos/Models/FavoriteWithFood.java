package group3.vute.travellaos.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class FavoriteWithFood {
    @Embedded
    public Favorite favorite;

    @Relation(
            parentColumn = "foodId",
            entityColumn = "id",
            entity = Food.class
    )
    public FoodWithCategory food;

    public FavoriteWithFood(Favorite favorite, FoodWithCategory food) {
        this.favorite = favorite;
        this.food = food;
    }

    public Favorite getFavorite() {
        return favorite;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    public FoodWithCategory getFoodWithCategory() {
        return food;
    }

    public void setFoodWithCategory(FoodWithCategory food) {
        this.food = food;
    }
}
