package group3.vute.travellaos.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Dialog.PlaceDetailDialog;
import group3.vute.travellaos.Models.PlaceWithCategory;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ItemPlaceNewBinding;

public class PlaceNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<PlaceWithCategory> listData;

    public PlaceNewAdapter(List<PlaceWithCategory> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlaceNewBinding orderBinding = ItemPlaceNewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderViewHolder(orderBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlaceWithCategory item = listData.get(position);
        if (item == null)
            return;
        OrderViewHolder orderViewHolder = (OrderViewHolder) holder;
        orderViewHolder.binding.txtName.setText(item.getPlace().getName());
        orderViewHolder.binding.ratingBar.setRating(item.getPlace().getAvgRating());
        orderViewHolder.binding.ratingText.setText("(" + item.getPlace().getAvgRating() + ")");
        orderViewHolder.binding.txtCategoryName.setText(item.getCategory() != null ? item.getCategory().getName() : "");
        CommonUtils.loadImage(orderViewHolder.binding.pic, item.getPlace().getImage());
    }

    @Override
    public int getItemCount() {
        if (listData != null)
            return listData.size();
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private final ItemPlaceNewBinding binding;

        public OrderViewHolder(@NonNull ItemPlaceNewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.item.setOnClickListener(v -> new PlaceDetailDialog(v.getContext(), listData.get(getAdapterPosition()).getPlace()).show());
            binding.btnFavorite.setOnClickListener(v -> CommonUtils.addPlaceToFavorite(v.getContext(), listData.get(getAdapterPosition()).getPlace().getId()));
        }
    }
}
