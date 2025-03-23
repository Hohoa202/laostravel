package group3.vute.travellaos.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import group3.vute.travellaos.Models.Place;
import group3.vute.travellaos.Models.PlaceWithCategory;

@Dao
public interface PlaceDao {
    @Insert
    void insert(Place place);

    @Transaction
    @Query("SELECT * from places ORDER BY id DESC")
    List<PlaceWithCategory> getAll();

    @Transaction
    @Query("SELECT * from places ORDER BY id DESC LIMIT 4")
    List<PlaceWithCategory> getNew();

    @Update
    void update(Place data);

    @Delete
    void delete(Place data);

    @Query("DELETE FROM places WHERE id = :id")
    void deleteById(int id);
}
