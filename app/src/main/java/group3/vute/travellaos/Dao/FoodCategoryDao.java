package group3.vute.travellaos.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import group3.vute.travellaos.Models.CategoryWithFoods;
import group3.vute.travellaos.Models.FoodCategory;

@Dao
public interface FoodCategoryDao {
    @Insert
    void insert(FoodCategory foodCategory);

    @Query("SELECT * FROM food_categories ORDER BY id DESC")
    List<FoodCategory> getAll();

    @Transaction
    @Query("SELECT * FROM food_categories")
    List<CategoryWithFoods> getCategoriesWithFoods();

    @Update
    void update(FoodCategory placeCategory);

    @Delete
    void delete(FoodCategory placeCategory);

    @Query("DELETE FROM food_categories WHERE id = :id")
    void deleteById(int id);
}
