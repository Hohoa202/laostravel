package group3.vute.travellaos.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import group3.vute.travellaos.Dialog.PlaceDetailDialog;
import group3.vute.travellaos.Models.Place;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ItemPlaceGridBinding;

public class PlaceItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Place> data;

    public PlaceItemAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<Place> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlaceGridBinding itemOrderProductsBinding = ItemPlaceGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductHolder(itemOrderProductsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Place item = data.get(position);
        if (item == null)
            return;
        ProductHolder holder1 = (ProductHolder) holder;
        holder1.binding.txtName.setText(item.getName());
        holder1.binding.ratingBar.setRating(item.getAvgRating());
        holder1.binding.ratingText.setText("(" + item.getAvgRating() + ")");
        holder1.binding.txtAddress.setText(item.getAddress());
        CommonUtils.loadImage(holder1.binding.pic, item.getImage());
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private ItemPlaceGridBinding binding;

        public ProductHolder(@NonNull ItemPlaceGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.item.setOnClickListener(v -> new PlaceDetailDialog(v.getContext(), data.get(getAdapterPosition())).show());
            binding.btnFavorite.setOnClickListener(v -> CommonUtils.addPlaceToFavorite(v.getContext(), data.get(getAdapterPosition()).getId()));
        }
    }
}
