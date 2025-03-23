package group3.vute.travellaos.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import group3.vute.travellaos.Activity.SearchFoodActivity;
import group3.vute.travellaos.Adapters.FoodCategoryAdapter;
import group3.vute.travellaos.Adapters.FoodRatingAdapter;
import group3.vute.travellaos.Dao.FoodCategoryDao;
import group3.vute.travellaos.Dao.FoodDao;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.CategoryWithFoods;
import group3.vute.travellaos.Models.Food;
import group3.vute.travellaos.Models.FoodRatingAvg;
import group3.vute.travellaos.Models.FoodWithCategory;
import group3.vute.travellaos.databinding.FragmentFoodBinding;

public class FoodFragment extends Fragment {
    private FragmentFoodBinding binding;
    private AppDatabase db;
    private List<FoodWithCategory> listData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFoodBinding.inflate(inflater, container, false);
        db = AppDatabase.getInstance(getActivity());
        listData = new ArrayList<>();
        getData();
        binding.search.edSearch.setHint("Tìm kiếm món ăn ẩm thực...");
        binding.search.edSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                startActivity(new Intent(getContext(), SearchFoodActivity.class));
            }
        });
        binding.search.edSearch.setOnClickListener(v -> startActivity(new Intent(getContext(), SearchFoodActivity.class)));
        return binding.getRoot();
    }

    private void getCategoryData() {
        FoodCategoryDao dao = db.foodCategoryDao();
        List<CategoryWithFoods> categoryList = dao.getCategoriesWithFoods();

        for (CategoryWithFoods category : categoryList) {
            List<Integer> ids = category.getFood().stream().map(Food::getId).collect(Collectors.toList());
            List<FoodRatingAvg> listRating = db.reviewDao().getFoodRatingAvg(ids);

            Map<Integer, Food> map = new HashMap<>();
            for (Food item : category.getFood()) {
                map.put(item.getId(), item);
            }
            for (FoodRatingAvg rating : listRating) {
                Food item = map.get(rating.getFoodId());
                if (item != null) {
                    item.setAvgRating(rating.getAvgRating());
                }
            }
        }

        FoodCategoryAdapter adapter = new FoodCategoryAdapter(categoryList.stream().filter(item -> !item.getFood().isEmpty()).collect(Collectors.toList()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewCategories.setLayoutManager(layoutManager);
        binding.recyclerViewCategories.setAdapter(adapter);
    }

    private void getData() {
        FoodDao dao = db.foodDao();
        listData = dao.getAll();

        List<Integer> ids = listData.stream().map(item -> item.getFood().getId()).collect(Collectors.toList());
        List<FoodRatingAvg> listRating = db.reviewDao().getFoodRatingAvg(ids);
        Map<Integer, FoodWithCategory> map = new HashMap<>();
        for (FoodWithCategory item : listData) {
            map.put(item.getFood().getId(), item);
        }
        for (FoodRatingAvg rating : listRating) {
            FoodWithCategory item = map.get(rating.getFoodId());
            if (item != null) {
                item.getFood().setAvgRating(rating.getAvgRating());
            }
        }

        listData.sort((a, b) -> Double.compare(b.getFood().getAvgRating(), a.getFood().getAvgRating()));
        List<FoodWithCategory> top6Rating = listData.subList(0, Math.min(6, listData.size()));

        FoodRatingAdapter adapter = new FoodRatingAdapter(top6Rating);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.placeRating.setLayoutManager(layout);
        binding.placeRating.setAdapter(adapter);

        // Get list category with foods
        getCategoryData();
    }

    @Override
    public void onResume() {
        getData();
        super.onResume();
    }
}
