package group3.vute.travellaos.Admin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Admin.Dialog.CategoryAddDialog;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.PlaceCategory;
import group3.vute.travellaos.Dao.PlaceCategoryDao;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.AdminItemRecyclerViewBinding;

public class PlaceCategoryAdapter extends RecyclerView.Adapter<PlaceCategoryAdapter.viewHolder> {
    private List<PlaceCategory> categoryList;
    private CategoryAddDialog.OnCategoryAddedListener onCategoryAddedListener;

    public PlaceCategoryAdapter(List<PlaceCategory> categoryList, CategoryAddDialog.OnCategoryAddedListener listener) {
        this.categoryList = categoryList;
        this.onCategoryAddedListener = listener;
    }

    public void changeData(List<PlaceCategory> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceCategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminItemRecyclerViewBinding binding = AdminItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceCategoryAdapter.viewHolder holder, int position) {
        PlaceCategory category = categoryList.get(position);
        int pos = holder.getAdapterPosition();

        holder.binding.name.setText(category.getName());
        holder.binding.number.setText(String.valueOf(position + 1));

        holder.binding.btnEdit.setOnClickListener(v -> {
            if (position != RecyclerView.NO_POSITION) {
                Context context = v.getContext();
                CategoryAddDialog dialog = new CategoryAddDialog(context, this.onCategoryAddedListener, category);
                dialog.setIsEditing(true);
                dialog.show();
            }
        });

        holder.binding.btnRemove.setOnClickListener(v -> {
            if (pos != RecyclerView.NO_POSITION) {
                PlaceCategory findData = categoryList.get(pos);
                CommonUtils.showConfirmDialog(v.getContext(), "Xác nhận xóa danh mục", "Bạn chắc chắn muốn xóa danh mục \n\"" + findData.getName() + "\"?\nDữ liệu sẽ không thể phục hồi!", () -> {
                    try {
                        AppDatabase db = AppDatabase.getInstance(v.getContext());
                        PlaceCategoryDao dao = db.placeCategoryDao();
                        dao.delete(findData);

                        if (pos >= 0 && pos < categoryList.size()) {
                            categoryList.remove(pos);
                            notifyDataSetChanged();
                        } else {
                            CommonUtils.showToast(v.getContext(), "Không thể xóa, dữ liệu không hợp lệ!");
                        }

                        CommonUtils.showToast(v.getContext(), "Đã xóa danh mục");
                    } catch (Exception e) {
                        CommonUtils.showToast(v.getContext(), "Lỗi hệ thống!");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        AdminItemRecyclerViewBinding binding;

        public viewHolder(@NonNull AdminItemRecyclerViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
