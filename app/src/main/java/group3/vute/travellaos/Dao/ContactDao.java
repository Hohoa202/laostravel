package group3.vute.travellaos.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import group3.vute.travellaos.Models.Contact;

@Dao
public interface ContactDao {
    @Insert
    void insert(Contact data);

    @Transaction
    @Query("SELECT * FROM contacts ORDER BY id DESC")
    List<Contact> getAll();

    @Update
    void update(Contact data);
}
