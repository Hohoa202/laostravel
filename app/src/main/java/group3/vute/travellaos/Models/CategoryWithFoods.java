package group3.vute.travellaos.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithFoods {
    @Relation(
            parentColumn = "id",
            entityColumn = "categoryId"
    )
    public List<Food> food;

    @Embedded
    public FoodCategory category;

    public List<Food> getFood() {
        return food;
    }

    public void setFood(List<Food> place) {
        this.food = place;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }
}
