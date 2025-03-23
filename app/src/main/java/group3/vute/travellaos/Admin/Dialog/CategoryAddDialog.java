package group3.vute.travellaos.Admin.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;

import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.PlaceCategory;
import group3.vute.travellaos.Dao.PlaceCategoryDao;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.AdminDialogAddCategoryBinding;

public class CategoryAddDialog extends Dialog {
    private AdminDialogAddCategoryBinding binding;
    private OnCategoryAddedListener onCategoryAddedListener;
    private PlaceCategory category;
    private PlaceCategoryDao dao;
    private Boolean isEditing = false;

    public void setIsEditing(boolean editing) {
        isEditing = editing;
        binding.labelHeader.setText("Chỉnh sửa danh mục");
        binding.btnCheckAdd.setText(isEditing ? "Chỉnh sửa" : "Thêm mới");
    }

    public CategoryAddDialog(@NonNull Context context, OnCategoryAddedListener listener, PlaceCategory category) {
        super(context);
        binding = AdminDialogAddCategoryBinding.inflate(LayoutInflater.from(context));
        this.onCategoryAddedListener = listener;
        this.category = category;
        AppDatabase db = AppDatabase.getInstance(getContext());
        dao = db.placeCategoryDao();

        if (category != null) {
            binding.categoryName.setText(category.getName());
        }

        binding.btnCheckAdd.setOnClickListener(view -> {
            String name = binding.categoryName.getText().toString().trim();
            if (name.isEmpty()) {
                binding.categoryNameLayout.setHelperText("Vùi lòng nhập tên danh mục");
            } else {
                binding.categoryNameLayout.setHelperText("");
                if (category == null) {
                    addCategory(name);
                } else {
                    category.setName(name);
                    updateCategory(category);
                }
            }
        });

        // Set event
        binding.btnClose.setOnClickListener(v -> dismiss());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getWindow().setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
    }

    private void addCategory(String name) {
        PlaceCategory placeCategory = new PlaceCategory(name);
        try {
            dao.insert(placeCategory);
            if (onCategoryAddedListener != null) {
                onCategoryAddedListener.onCategoryAdded();
            }
            CommonUtils.showToast(getContext(), "Danh mục địa điểm đã được tạo");
            dismiss();
        } catch (Exception e) {
            CommonUtils.showToast(getContext(), "Lỗi hệ thống");
        }
    }

    private void updateCategory(PlaceCategory category) {
        try {
            dao.update(category);
            if (onCategoryAddedListener != null) {
                onCategoryAddedListener.onCategoryAdded();
            }
            CommonUtils.showToast(getContext(), "Danh mục địa điểm đã được cập nhật");
            dismiss();
        } catch (Exception e) {
            CommonUtils.showToast(getContext(), "Lỗi hệ thống");
        }
    }

    public interface OnCategoryAddedListener {
        void onCategoryAdded();
    }
}
