package group3.vute.travellaos.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import group3.vute.travellaos.Dialog.FoodDetailDialog;
import group3.vute.travellaos.Models.Food;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ItemFoodGridBinding;

public class FoodItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Food> data;

    public FoodItemAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<Food> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodGridBinding binding = ItemFoodGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Food item = data.get(position);
        if (item == null)
            return;
        Holder holder1 = (Holder) holder;
        holder1.binding.txtName.setText(item.getName());
        holder1.binding.txtOrigin.setText(item.getOrigin());
        holder1.binding.ratingBar.setRating(item.getAvgRating());
        holder1.binding.ratingText.setText("(" + item.getAvgRating() + ")");
        CommonUtils.loadImage(holder1.binding.pic, item.getImage());
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ItemFoodGridBinding binding;

        public Holder(@NonNull ItemFoodGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.item.setOnClickListener(v -> new FoodDetailDialog(v.getContext(), data.get(getAdapterPosition())).show());
            binding.btnFavorite.setOnClickListener(v -> CommonUtils.addFoodToFavorite(v.getContext(), data.get(getAdapterPosition()).getId()));
        }
    }
}
