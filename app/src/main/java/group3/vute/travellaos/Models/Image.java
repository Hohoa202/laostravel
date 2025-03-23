package group3.vute.travellaos.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "slide_images")
public class Image implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String image;
    private Integer order;

    public Image(String image, Integer order) {
        this.image = image;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
