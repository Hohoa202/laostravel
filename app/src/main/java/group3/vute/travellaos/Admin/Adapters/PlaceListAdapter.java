package group3.vute.travellaos.Admin.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Admin.Activity.CreateOrUpdatePlaceActivity;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Dao.PlaceDao;
import group3.vute.travellaos.Models.PlaceWithCategory;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.AdminItemPlaceListBinding;

public class PlaceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PlaceWithCategory> listData;

    public PlaceListAdapter(List<PlaceWithCategory> listData) {
        this.listData = listData;
    }

    public void setData(List<PlaceWithCategory> data) {
        this.listData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminItemPlaceListBinding binding = AdminItemPlaceListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PlaceListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlaceWithCategory item = listData.get(position);
        if (item == null) return;

        PlaceListAdapter.ViewHolder viewHolder = (PlaceListAdapter.ViewHolder) holder;
        viewHolder.binding.productName.setText(item.getPlace().getName());
        viewHolder.binding.description2.setText(item.getPlace().getDescription());
        viewHolder.binding.categoryName.setText(item.getCategory() != null ? item.getCategory().getName() : "");
        viewHolder.binding.address.setText(item.getPlace().getAddress());
        CommonUtils.loadImage(viewHolder.binding.imgProduct, item.getPlace().getImage());

        viewHolder.binding.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                PlaceWithCategory data = listData.get(pos);
                CommonUtils.showConfirmDialog(v.getContext(), "Xác nhận xóa địa điểm", "Bạn chắc chắn muốn địa điểm \n\"" + data.getPlace().getName() + "\"?\nDữ liệu sẽ không thể phục hồi!", () -> {
                    AppDatabase db = AppDatabase.getInstance(v.getContext());
                    PlaceDao dao = db.placeDao();
                    try {
                        dao.delete(data.getPlace());
                        db.favoriteDao().deleteByPlace(data.getPlace().getId());
                        if (pos >= 0 && pos < listData.size()) {
                            listData.remove(pos);
                            notifyDataSetChanged();
                        } else {
                            CommonUtils.showToast(v.getContext(), "Không thể xóa, dữ liệu không hợp lệ!");
                        }
                        CommonUtils.showToast(v.getContext(), "Địa điểm đã bị xóa");
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
        private AdminItemPlaceListBinding binding;

        public ViewHolder(@NonNull AdminItemPlaceListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            /**
             * Click edit
             */
            binding.btnEdit.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    PlaceWithCategory item = listData.get(position);
                    binding.rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_24, 0);
                    binding.boxDetail.setVisibility(View.GONE);
                    Intent intent = new Intent(v.getContext(), CreateOrUpdatePlaceActivity.class);
                    intent.putExtra("dataEdit", item.getPlace());
                    v.getContext().startActivity(intent);
                }
            });

            /**
             * Click item show detail
             */
            binding.item.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
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
