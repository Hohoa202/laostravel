package group3.vute.travellaos.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import group3.vute.travellaos.Models.Food;
import group3.vute.travellaos.Models.FoodWithCategory;

@Dao
public interface FoodDao {
    @Insert
    void insert(Food food);

    @Transaction
    @Query("SELECT * from foods ORDER BY id DESC")
    List<FoodWithCategory> getAll();

    @Transaction
    @Query("SELECT * from foods ORDER BY id DESC LIMIT 4")
    List<FoodWithCategory> getNew();

    @Update
    void update(Food data);

    @Delete
    void delete(Food data);

    @Query("DELETE FROM foods WHERE id = :id")
    void deleteById(int id);
}
