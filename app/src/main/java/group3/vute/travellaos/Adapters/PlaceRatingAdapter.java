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
import group3.vute.travellaos.databinding.ItemPlaceRatingBinding;

public class PlaceRatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PlaceWithCategory> listData;

    public PlaceRatingAdapter(List<PlaceWithCategory> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlaceRatingBinding orderBinding = ItemPlaceRatingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(orderBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlaceWithCategory item = listData.get(position);
        if (item == null)
            return;
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.binding.ratingBar.setRating(item.getPlace().getAvgRating());
        viewHolder.binding.ratingText.setText("(" + item.getPlace().getAvgRating() + ")");
        viewHolder.binding.txtProductName.setText(item.getPlace().getName());
        CommonUtils.loadImage(viewHolder.binding.pic, item.getPlace().getImage());
    }

    @Override
    public int getItemCount() {
        if (listData != null)
            return listData.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPlaceRatingBinding binding;

        public ViewHolder(@NonNull ItemPlaceRatingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.item.setOnClickListener(v -> new PlaceDetailDialog(v.getContext(), listData.get(getAdapterPosition()).getPlace()).show());
            binding.btnFavorite.setOnClickListener(v -> CommonUtils.addPlaceToFavorite(v.getContext(), listData.get(getAdapterPosition()).getPlace().getId()));
        }
    }
}
