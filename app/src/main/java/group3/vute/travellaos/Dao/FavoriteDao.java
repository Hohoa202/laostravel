package group3.vute.travellaos.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import group3.vute.travellaos.Models.Favorite;
import group3.vute.travellaos.Models.FavoriteWithFood;
import group3.vute.travellaos.Models.FavoriteWithPlace;

@Dao
public interface FavoriteDao {
    @Insert
    void insert(Favorite data);

    @Transaction
    @Query("SELECT * from favorites ORDER BY id DESC")
    List<Favorite> getAll();

    @Transaction
    @Query("SELECT * from favorites WHERE userId = :userId AND foodId IS NULL ORDER BY id DESC")
    List<FavoriteWithPlace> getWithPlace(int userId);

    @Transaction
    @Query("SELECT * from favorites WHERE userId = :userId AND placeId IS NULL ORDER BY id DESC")
    List<FavoriteWithFood> getWithFood(int userId);

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE placeId = :placeId AND userId = :userId)")
    boolean checkPlaceExist(int placeId, int userId);

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE foodId = :foodId AND userId = :userId)")
    boolean checkFoodExist(int foodId, int userId);

    @Update
    void update(Favorite data);

    @Delete
    void delete(Favorite data);

    @Query("DELETE FROM favorites WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM favorites WHERE placeId = :placeId")
    void deleteByPlace(int placeId);

    @Query("DELETE FROM favorites WHERE foodId = :foodId")
    void deleteByFood(int foodId);
}
