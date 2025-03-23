package group3.vute.travellaos.Models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity(tableName = "foods")
public class Food implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int categoryId;

    private String name;

    private String description;

    private String origin;

    private String image;

    public float avgRating = 0;

    private long createdAt;

    private long updatedAt;

    // Constructor, Getters & Setters
    @Ignore
    public Food() {
    }

    @Ignore
    public Food(int categoryId, String name, String description, String origin, String image) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.origin = origin;
        this.image = image;
    }

    public Food(int id, int categoryId, String name, String description, String origin, String image, long createdAt, long updatedAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.origin = origin;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getAvgRating() {
        return new BigDecimal(avgRating)
                .setScale(1, RoundingMode.HALF_UP)
                .floatValue();
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
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


