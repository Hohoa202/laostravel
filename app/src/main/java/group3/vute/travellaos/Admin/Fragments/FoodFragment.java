package group3.vute.travellaos.Admin.Fragments;

import android.content.Intent;
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

import group3.vute.travellaos.Admin.Activity.CreateOrUpdateFoodActivity;
import group3.vute.travellaos.Admin.Adapters.FoodListAdapter;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Dao.FoodDao;
import group3.vute.travellaos.Models.FoodWithCategory;
import group3.vute.travellaos.databinding.AdminFragmentPlaceAndFoodBinding;

public class FoodFragment extends Fragment {
    private AdminFragmentPlaceAndFoodBinding binding;
    private FoodListAdapter adapter;
    private List<FoodWithCategory> listData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AdminFragmentPlaceAndFoodBinding.inflate(inflater, container, false);
        listData = new ArrayList<>();
        adapter = new FoodListAdapter(listData);
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.list.setAdapter(adapter);

        binding.btnAdd.setOnClickListener(v -> startActivity(new Intent(getContext(), CreateOrUpdateFoodActivity.class)));
        binding.titleActivity.setText("Quản lý ẩm thực - món ăn");
        binding.searchBox.edSearch.setHint("Nhập tên món ăn cần tìm...");
        binding.searchBox.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchData(s.toString());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void searchData(String newText) {
        List<FoodWithCategory> data = new ArrayList<>();
        for (FoodWithCategory item : listData) {
            if (item.getFood().getName().toLowerCase().contains(newText.toLowerCase())
                    || item.getFood().getDescription().toLowerCase().contains(newText.toLowerCase())
                    || item.getFood().getOrigin().toLowerCase().contains(newText.toLowerCase())
            ) {
                data.add(item);
            }
        }
        adapter.setData(data);
        binding.emptyProduct.setVisibility(View.GONE);
        if (data.isEmpty()) binding.emptyProduct.setVisibility(View.VISIBLE);
    }

    private void getData() {
        AppDatabase db = AppDatabase.getInstance(getActivity());
        FoodDao dao = db.foodDao();
        listData = dao.getAll();
        adapter.setData(listData);
    }
}