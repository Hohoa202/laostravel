package group3.vute.travellaos.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Dialog.FoodDetailDialog;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.Favorite;
import group3.vute.travellaos.Dao.FavoriteDao;
import group3.vute.travellaos.Models.FavoriteWithFood;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ItemFavoriteFoodBinding;
import group3.vute.travellaos.databinding.ItemFavoritePlaceBinding;

public class FavoriteFoodAdapter extends RecyclerView.Adapter<FavoriteFoodAdapter.ViewHolder> {
    private List<FavoriteWithFood> listData;
    private FoodDetailDialog dialog;

    public FavoriteFoodAdapter(List<FavoriteWithFood> listData) {
        this.listData = listData;
    }

    public void setData(List<FavoriteWithFood> data) {
        this.listData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteFoodBinding binding = ItemFavoriteFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteWithFood item = listData.get(position);
        if (item == null) {
            return;
        }

        if (item.getFoodWithCategory() != null) {
            holder.binding.txtName.setText(item.getFoodWithCategory().getFood().getName());
            holder.binding.txtCategoryName.setText(item.getFoodWithCategory().getCategory() != null ? item.getFoodWithCategory().getCategory().getName() : "");
            holder.binding.txtOrigin.setText(item.getFoodWithCategory().getFood().getOrigin());
            CommonUtils.loadImage(holder.binding.image, item.getFoodWithCategory().getFood().getImage());
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
        ItemFavoriteFoodBinding binding;

        public ViewHolder(@NonNull ItemFavoriteFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.item.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (dialog != null && dialog.isShowing())
                        dialog.cancel();
                    dialog = new FoodDetailDialog(v.getContext(), listData.get(position).getFoodWithCategory().getFood());
                    dialog.show();
                }
            });
        }
    }
}
