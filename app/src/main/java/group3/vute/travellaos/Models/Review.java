package group3.vute.travellaos.Models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "reviews")
public class Review implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;

    private Integer placeId; // Cho phép null nếu là đánh giá món ăn

    private Integer foodId; // Cho phép null nếu là đánh giá địa điểm

    private int rating;

    private String content;

    private String createdAt;

    // Constructor, Getters & Setters

    @Ignore
    public Review() {
    }

    public Review(int userId, Integer placeId, Integer foodId, int rating, String content, String createdAt) {
        this.userId = userId;
        this.placeId = placeId;
        this.foodId = foodId;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String comment) {
        this.content = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

