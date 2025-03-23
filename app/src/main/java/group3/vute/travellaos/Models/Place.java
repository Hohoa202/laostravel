package group3.vute.travellaos.Models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity(tableName = "places")

public class Place implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int categoryId;

    @Ignore
    public String categoryName;

    private String description;

    private String address;

    private double latitude;

    private double longitude;

    private String image;

    public float avgRating = 0;

    private long createdAt;

    private long updatedAt;

    @Ignore
    public Place() {
    }

    @Ignore
    public Place(String name, int categoryId, String description, String address, String image) {
        this.name = name;
        this.categoryId = categoryId;
        this.description = description;
        this.address = address;
        this.image = image;
    }

    // Constructor, Getters & Setters
    public Place(int id, int categoryId, String name, String description, String address, double latitude, double longitude, String image, long createdAt, long updatedAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public float getAvgRating() {
        return new BigDecimal(avgRating)
                .setScale(1, RoundingMode.HALF_UP)
                .floatValue();
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }
}

