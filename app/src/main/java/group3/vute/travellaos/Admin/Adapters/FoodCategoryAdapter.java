package group3.vute.travellaos.Admin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Admin.Dialog.FoodCategoryAddDialog;
import group3.vute.travellaos.Dao.FoodCategoryDao;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.FoodCategory;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.AdminItemRecyclerViewBinding;

public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.viewHolder> {
    private List<FoodCategory> categoryList;
    private FoodCategoryAddDialog.OnCategoryAddedListener onCategoryAddedListener;

    public FoodCategoryAdapter(List<FoodCategory> categoryList, FoodCategoryAddDialog.OnCategoryAddedListener listener) {
        this.categoryList = categoryList;
        this.onCategoryAddedListener = listener;
    }

    public void changeData(List<FoodCategory> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodCategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminItemRecyclerViewBinding binding = AdminItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodCategoryAdapter.viewHolder holder, int position) {
        FoodCategory category = categoryList.get(position);
        int pos = holder.getAdapterPosition();

        holder.binding.name.setText(category.getName());
        holder.binding.number.setText(String.valueOf(position + 1));
        holder.binding.image.setImageResource(R.drawable.ic_clarify);

        holder.binding.btnEdit.setOnClickListener(v -> {
            if (position != RecyclerView.NO_POSITION) {
                Context context = v.getContext();
                FoodCategoryAddDialog dialog = new FoodCategoryAddDialog(context, this.onCategoryAddedListener, category);
                dialog.setIsEditing(true);
                dialog.show();
            }
        });

        holder.binding.btnRemove.setOnClickListener(v -> {
            if (pos != RecyclerView.NO_POSITION) {
                FoodCategory findData = categoryList.get(pos);
                CommonUtils.showConfirmDialog(v.getContext(), "Xác nhận xóa danh mục", "Bạn chắc chắn muốn xóa danh mục \n\"" + findData.getName() + "\"?\nDữ liệu sẽ không thể phục hồi!", () -> {
                    try {
                        AppDatabase db = AppDatabase.getInstance(v.getContext());
                        FoodCategoryDao dao = db.foodCategoryDao();
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
