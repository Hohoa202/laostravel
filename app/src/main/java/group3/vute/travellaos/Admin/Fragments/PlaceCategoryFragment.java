package group3.vute.travellaos.Admin.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import group3.vute.travellaos.Admin.Adapters.PlaceCategoryAdapter;
import group3.vute.travellaos.Admin.Dialog.CategoryAddDialog;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.PlaceCategory;
import group3.vute.travellaos.Dao.PlaceCategoryDao;
import group3.vute.travellaos.databinding.AdminFragmentCategoryBinding;

public class PlaceCategoryFragment extends Fragment implements CategoryAddDialog.OnCategoryAddedListener {
    private AdminFragmentCategoryBinding binding;
    private PlaceCategoryAdapter categoryAdapter;
    private List<PlaceCategory> categoryList;
    private CategoryAddDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = AdminFragmentCategoryBinding.inflate(inflater, container, false);
        categoryList = new ArrayList<>();
        categoryAdapter = new PlaceCategoryAdapter(categoryList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(categoryAdapter);
        getCategoryData();
        binding.searchBox.edSearch.setHint("Nhập tên danh mục cần tìm");

        binding.btnAdd.setOnClickListener(v -> {
            dialog = new CategoryAddDialog(getContext(), this, null);
            dialog.show();
        });

        binding.searchBox.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchCategory(s.toString());
            }
        });

        return binding.getRoot();
    }

    private void getCategoryData() {
        AppDatabase db = AppDatabase.getInstance(getContext());
        PlaceCategoryDao dao = db.placeCategoryDao();
        categoryList = dao.getAll();
        categoryAdapter.changeData(categoryList);
    }

    private void searchCategory(String newText) {
        List<PlaceCategory> categories = new ArrayList<>();
        for (PlaceCategory item : categoryList) {
            if (item.getName().toLowerCase().contains(newText.toLowerCase())
            ) {
                categories.add(item);
            }
        }
        categoryAdapter.changeData(categories);
        binding.emptyProduct.setVisibility(View.GONE);
        if (categories.isEmpty()) binding.emptyProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCategoryAdded() {
        getCategoryData();
    }

    @Override
    public void onDestroy() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        super.onDestroy();
    }
}