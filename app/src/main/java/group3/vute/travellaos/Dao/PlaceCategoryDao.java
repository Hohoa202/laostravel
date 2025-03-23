package group3.vute.travellaos.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import group3.vute.travellaos.Models.CategoryWithPlaces;
import group3.vute.travellaos.Models.PlaceCategory;

@Dao
public interface PlaceCategoryDao {
    @Insert
    void insert(PlaceCategory placeCategory);

    @Query("SELECT * FROM place_categories ORDER BY id DESC")
    List<PlaceCategory> getAll();

    @Transaction
    @Query("SELECT * FROM place_categories")
    List<CategoryWithPlaces> getCategoriesWithPlaces();

    @Update
    void update(PlaceCategory placeCategory);

    @Delete
    void delete(PlaceCategory placeCategory);

    @Query("DELETE FROM place_categories WHERE id = :id")
    void deleteById(int id);
}
