package group3.vute.travellaos.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import group3.vute.travellaos.Dialog.PlaceDetailDialog;
import group3.vute.travellaos.Models.PlaceWithCategory;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ItemPlaceListBinding;

public class PlaceItemSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PlaceWithCategory> data;

    public PlaceItemSearchAdapter() {
        data = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<PlaceWithCategory> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlaceListBinding binding = ItemPlaceListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlaceWithCategory item = data.get(position);
        if (item == null)
            return;
        Holder holder1 = (Holder) holder;
        holder1.binding.name.setText(item.getPlace().getName());
        holder1.binding.categoryName.setText(item.getCategory() != null ? item.getCategory().getName() : "");
        holder1.binding.address.setText(item.getPlace().getAddress());
        CommonUtils.loadImage(holder1.binding.image, item.getPlace().getImage());
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ItemPlaceListBinding binding;

        public Holder(@NonNull ItemPlaceListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.item.setOnClickListener(v -> new PlaceDetailDialog(v.getContext(), data.get(getAdapterPosition()).getPlace()).show());
            binding.btnFavorite.setOnClickListener(v -> CommonUtils.addPlaceToFavorite(v.getContext(), data.get(getAdapterPosition()).getPlace().getId()));
        }
    }
}
