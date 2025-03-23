package group3.vute.travellaos.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import group3.vute.travellaos.Models.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User loginUser(String email, String password);

    @Query("SELECT * FROM users WHERE id = :id")
    User find(int id);

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    boolean checkEmail(String email);

    @Update
    void update(User data);
}
