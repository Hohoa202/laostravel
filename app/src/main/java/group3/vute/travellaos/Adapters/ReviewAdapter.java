package group3.vute.travellaos.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Models.ReviewWithUser;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ItemReviewBinding;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ReviewWithUser> data;

    public ReviewAdapter(List<ReviewWithUser> data) {
        this.data = data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ReviewWithUser> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReviewBinding binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReviewWithUser item = data.get(position);
        if (item == null)
            return;
        Holder holder1 = (Holder) holder;
        holder1.binding.fullName.setText(item.getUser().getFullName());
        holder1.binding.createdAt.setText(item.getReview().getCreatedAt());
        holder1.binding.content.setText(item.getReview().getContent());
        holder1.binding.rating.setRating((float) item.getReview().getRating());
        CommonUtils.loadImage(holder1.binding.avatar, item.getUser().getAvatar());
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private final ItemReviewBinding binding;

        public Holder(@NonNull ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
