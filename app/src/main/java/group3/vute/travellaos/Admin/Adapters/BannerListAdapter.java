package group3.vute.travellaos.Admin.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Admin.Activity.CreateOrUpdateBannerActivity;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.Image;
import group3.vute.travellaos.Dao.ImageDao;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.AdminItemBannerListBinding;

public class BannerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Image> listData;

    public BannerListAdapter(List<Image> listData) {
        this.listData = listData;
    }

    public void setData(List<Image> data) {
        this.listData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminItemBannerListBinding binding = AdminItemBannerListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BannerListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Image item = listData.get(position);
        if (item == null) return;

        BannerListAdapter.ViewHolder viewHolder = (BannerListAdapter.ViewHolder) holder;
        CommonUtils.loadImage(viewHolder.binding.image, item.getImage());

        viewHolder.binding.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                Image data = listData.get(pos);
                CommonUtils.showConfirmDialog(v.getContext(), "Xác nhận xóa hình ảnh", "Dữ liệu sẽ không thể phục hồi!", () -> {
                    AppDatabase db = AppDatabase.getInstance(v.getContext());
                    ImageDao dao = db.imageDao();
                    try {
                        dao.delete(data);
                        if (pos >= 0 && pos < listData.size()) {
                            listData.remove(pos);
                            notifyDataSetChanged();
                        } else {
                            CommonUtils.showToast(v.getContext(), "Không thể xóa, dữ liệu không hợp lệ!");
                        }
                        CommonUtils.showToast(v.getContext(), "Hình ảnh đã bị xóa");
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
        private AdminItemBannerListBinding binding;

        public ViewHolder(@NonNull AdminItemBannerListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            /**
             * Click edit
             */
            binding.btnEdit.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Image item = listData.get(position);
                    Intent intent = new Intent(v.getContext(), CreateOrUpdateBannerActivity.class);
                    intent.putExtra("dataEdit", item);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
