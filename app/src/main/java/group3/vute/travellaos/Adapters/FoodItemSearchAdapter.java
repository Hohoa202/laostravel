package group3.vute.travellaos.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import group3.vute.travellaos.Dialog.FoodDetailDialog;
import group3.vute.travellaos.Models.FoodWithCategory;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ItemFoodListBinding;

public class FoodItemSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FoodWithCategory> data;

    public FoodItemSearchAdapter() {
        data = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<FoodWithCategory> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodListBinding binding = ItemFoodListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodWithCategory item = data.get(position);
        if (item == null)
            return;
        Holder holder1 = (Holder) holder;
        holder1.binding.name.setText(item.getFood().getName());
        holder1.binding.categoryName.setText(item.getCategory() != null ? item.getCategory().getName() : "");
        holder1.binding.origin.setText(item.getFood().getOrigin());
        CommonUtils.loadImage(holder1.binding.image, item.getFood().getImage());
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ItemFoodListBinding binding;

        public Holder(@NonNull ItemFoodListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.item.setOnClickListener(v -> new FoodDetailDialog(v.getContext(), data.get(getAdapterPosition()).getFood()).show());
            binding.btnFavorite.setOnClickListener(v -> CommonUtils.addFoodToFavorite(v.getContext(), data.get(getAdapterPosition()).getFood().getId()));
        }
    }
}
