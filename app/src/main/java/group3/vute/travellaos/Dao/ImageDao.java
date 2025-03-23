package group3.vute.travellaos.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import group3.vute.travellaos.Models.Image;

@Dao
public interface ImageDao {
    @Insert
    void insert(Image data);

    @Transaction
    @Query("SELECT * FROM slide_images ORDER BY `order` ASC")
    List<Image> getAll();

    @Update
    void update(Image data);

    @Delete
    void delete(Image data);

    @Query("DELETE FROM slide_images WHERE id = :id")
    void deleteById(int id);
}
