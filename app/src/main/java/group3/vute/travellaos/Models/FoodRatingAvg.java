package group3.vute.travellaos.Models;

public class FoodRatingAvg {
    public Integer foodId;
    public float avgRating = 5;

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer placeId) {
        this.foodId = placeId;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }
}