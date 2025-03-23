package group3.vute.travellaos.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Dialog.PlaceDetailDialog;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.Favorite;
import group3.vute.travellaos.Dao.FavoriteDao;
import group3.vute.travellaos.Models.FavoriteWithPlace;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ItemFavoritePlaceBinding;

public class FavoritePlaceAdapter extends RecyclerView.Adapter<FavoritePlaceAdapter.ViewHolder> {
    private List<FavoriteWithPlace> listData;
    private PlaceDetailDialog dialog;

    public FavoritePlaceAdapter(List<FavoriteWithPlace> listData) {
        this.listData = listData;
    }

    public void setData(List<FavoriteWithPlace> data) {
        this.listData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoritePlaceBinding binding = ItemFavoritePlaceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteWithPlace item = listData.get(position);
        if (item == null) {
            return;
        }

        if (item.getPlaceWithCategory() != null) {
            holder.binding.txtName.setText(item.getPlaceWithCategory().getPlace().getName());
            holder.binding.txtCategoryName.setText(item.getPlaceWithCategory().getCategory() != null ? item.getPlaceWithCategory().getCategory().getName() : "");
            holder.binding.txtAddress.setText(item.getPlaceWithCategory().getPlace().getAddress());
            CommonUtils.loadImage(holder.binding.image, item.getPlaceWithCategory().getPlace().getImage());
        }

        holder.binding.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                Favorite data = listData.get(pos).getFavorite();
                AppDatabase db = AppDatabase.getInstance(v.getContext());
                FavoriteDao dao = db.favoriteDao();
                try {
                    dao.delete(data);
                    if (pos >= 0 && pos < listData.size()) {
                        listData.remove(pos);
                        notifyDataSetChanged();
                        CommonUtils.showToast(v.getContext(), "Đã xóa khỏi bộ sưu tập yêu thích");
                    } else {
                        CommonUtils.showToast(v.getContext(), "Không thể xóa, dữ liệu không hợp lệ!");
                    }
                } catch (Exception e) {
                    CommonUtils.showToast(v.getContext(), "Lỗi hệ thống");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData != null ? listData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFavoritePlaceBinding binding;

        public ViewHolder(@NonNull ItemFavoritePlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.item.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (dialog != null && dialog.isShowing())
                        dialog.cancel();
                    dialog = new PlaceDetailDialog(v.getContext(), listData.get(position).getPlaceWithCategory().getPlace());
                    dialog.show();
                }
            });
        }
    }
}
