package group3.vute.travellaos.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ReviewWithUser {
    @Embedded
    public Review review;

    @Relation(
            parentColumn = "userId",
            entityColumn = "id",
            entity = User.class
    )
    public User user;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
