package group3.vute.travellaos.Models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "food_categories")
public class FoodCategory implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private long createdAt;

    private long updatedAt;

    // Constructor, Getters & Setters
    public FoodCategory() {
    }

    @Ignore
    public FoodCategory(String name) {
        this.name = name;
    }

    public FoodCategory(int id, String name, long createdAt, long updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}



