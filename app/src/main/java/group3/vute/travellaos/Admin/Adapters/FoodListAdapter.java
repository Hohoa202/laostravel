package group3.vute.travellaos.Admin.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Admin.Activity.CreateOrUpdateFoodActivity;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Dao.FoodDao;
import group3.vute.travellaos.Models.FoodWithCategory;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.AdminItemFoodListBinding;

public class FoodListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FoodWithCategory> listData;

    public FoodListAdapter(List<FoodWithCategory> listData) {
        this.listData = listData;
    }

    public void setData(List<FoodWithCategory> data) {
        this.listData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminItemFoodListBinding binding = AdminItemFoodListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodWithCategory item = listData.get(position);
        if (item == null) return;

        FoodListAdapter.ViewHolder viewHolder = (FoodListAdapter.ViewHolder) holder;
        viewHolder.binding.productName.setText(item.getFood().getName());
        viewHolder.binding.description2.setText(item.getFood().getDescription());
        viewHolder.binding.categoryName.setText(item.getCategory() != null ? item.getCategory().getName() : "");
        viewHolder.binding.origin.setText(item.getFood().getOrigin());
        CommonUtils.loadImage(viewHolder.binding.imgProduct, item.getFood().getImage());

        viewHolder.binding.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                FoodWithCategory data = listData.get(pos);
                CommonUtils.showConfirmDialog(v.getContext(), "Xác nhận xóa món ăn", "Bạn chắc chắn muốn ăn \n\"" + data.getFood().getName() + "\"?\nDữ liệu sẽ không thể phục hồi!", () -> {
                    AppDatabase db = AppDatabase.getInstance(v.getContext());
                    FoodDao dao = db.foodDao();
                    try {
                        dao.delete(data.getFood());
                        db.favoriteDao().deleteByFood(data.getFood().getId());
                        if (pos >= 0 && pos < listData.size()) {
                            listData.remove(pos);
                            notifyDataSetChanged();
                        } else {
                            CommonUtils.showToast(v.getContext(), "Không thể xóa, dữ liệu không hợp lệ!");
                        }
                        CommonUtils.showToast(v.getContext(), "Món ăn đã bị xóa");
                    } catch (Exception e) {
                        CommonUtils.showToast(v.getContext(), "Lỗi hệ thống");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listData != null) return listData.size();
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private AdminItemFoodListBinding binding;

        public ViewHolder(@NonNull AdminItemFoodListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            /**
             * Click edit
             */
            binding.btnEdit.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    FoodWithCategory item = listData.get(position);
                    binding.rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_24, 0);
                    binding.boxDetail.setVisibility(View.GONE);
                    Intent intent = new Intent(v.getContext(), CreateOrUpdateFoodActivity.class);
                    intent.putExtra("dataEdit", item.getFood());
                    v.getContext().startActivity(intent);
                }
            });

            /**
             * Click item show detail
             */
            binding.item.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    FoodWithCategory item = listData.get(position);
                    int visibility = binding.boxDetail.getVisibility();
                    if (visibility == View.VISIBLE) {
                        binding.boxDetail.setVisibility(View.GONE);
                        binding.rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_24, 0);
                    } else {
                        binding.boxDetail.setVisibility(View.VISIBLE);
                        binding.rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_24, 0);
                    }
                }
            });
        }
    }
}
