package group3.vute.travellaos.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import group3.vute.travellaos.Models.FoodRatingAvg;
import group3.vute.travellaos.Models.PlaceRatingAvg;
import group3.vute.travellaos.Models.Review;
import group3.vute.travellaos.Models.ReviewWithUser;

@Dao
public interface ReviewDao {
    @Insert
    void insert(Review review);

    @Transaction
    @Query("SELECT * FROM reviews WHERE placeId = :placeId ORDER BY id DESC")
    List<ReviewWithUser> getByPlaceId(int placeId);

    @Transaction
    @Query("SELECT * FROM reviews WHERE foodId = :foodId ORDER BY id DESC")
    List<ReviewWithUser> getByFoodId(int foodId);

    @Transaction
    @Query("SELECT * FROM reviews WHERE userId = :userId AND placeId = :placeId ORDER BY id DESC LIMIT 1")
    Review getReviewedPlace(int userId, int placeId);

    @Transaction
    @Query("SELECT * FROM reviews WHERE userId = :userId AND foodId = :foodId ORDER BY id DESC LIMIT 1")
    Review getReviewedFood(int userId, int foodId);

    @Transaction
    @Query("SELECT placeId, COALESCE(AVG(rating), 5) AS avgRating FROM reviews WHERE placeId IN (:placeIds) GROUP BY placeId")
    List<PlaceRatingAvg> getPlaceRatingAvg(List<Integer> placeIds);

    @Transaction
    @Query("SELECT foodId, COALESCE(AVG(rating), 5) AS avgRating FROM reviews WHERE foodId IN (:foodId) GROUP BY foodId")
    List<FoodRatingAvg> getFoodRatingAvg(List<Integer> foodId);
}
