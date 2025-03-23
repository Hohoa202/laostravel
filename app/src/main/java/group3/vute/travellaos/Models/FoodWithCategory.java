package group3.vute.travellaos.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class FoodWithCategory {
    @Embedded
    public Food food;

    @Relation(
            parentColumn = "categoryId",
            entityColumn = "id"
    )
    public FoodCategory category;


    public FoodWithCategory(Food food, FoodCategory category) {
        this.food = food;
        this.category = category;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food place) {
        this.food = place;
    }
}
