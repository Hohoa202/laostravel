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

import group3.vute.travellaos.Activity.SearchPlaceActivity;
import group3.vute.travellaos.Adapters.PlaceCategoryAdapter;
import group3.vute.travellaos.Adapters.PlaceRatingAdapter;
import group3.vute.travellaos.Dao.PlaceCategoryDao;
import group3.vute.travellaos.Dao.PlaceDao;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.CategoryWithFoods;
import group3.vute.travellaos.Models.CategoryWithPlaces;
import group3.vute.travellaos.Models.Food;
import group3.vute.travellaos.Models.FoodRatingAvg;
import group3.vute.travellaos.Models.Place;
import group3.vute.travellaos.Models.PlaceRatingAvg;
import group3.vute.travellaos.Models.PlaceWithCategory;
import group3.vute.travellaos.databinding.FragmentPlaceBinding;

public class PlaceFragment extends Fragment {
    private FragmentPlaceBinding binding;
    private AppDatabase db;
    private List<PlaceWithCategory> listData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlaceBinding.inflate(inflater, container, false);
        db = AppDatabase.getInstance(getActivity());
        listData = new ArrayList<>();
        getData();
        binding.search.edSearch.setHint("Tìm kiếm địa điểm du lịch...");
        binding.search.edSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                startActivity(new Intent(getContext(), SearchPlaceActivity.class));
            }
        });
        binding.search.edSearch.setOnClickListener(v -> startActivity(new Intent(getContext(), SearchPlaceActivity.class)));
        return binding.getRoot();
    }

    private void getCategoryData() {
        PlaceCategoryDao dao = db.placeCategoryDao();
        List<CategoryWithPlaces> categoryList = dao.getCategoriesWithPlaces();

        for (CategoryWithPlaces category : categoryList) {
            List<Integer> ids = category.getPlace().stream().map(Place::getId).collect(Collectors.toList());
            List<PlaceRatingAvg> listRating = db.reviewDao().getPlaceRatingAvg(ids);

            Map<Integer, Place> map = new HashMap<>();
            for (Place item : category.getPlace()) {
                map.put(item.getId(), item);
            }
            for (PlaceRatingAvg rating : listRating) {
                Place item = map.get(rating.getPlaceId());
                if (item != null) {
                    item.setAvgRating(rating.getAvgRating());
                }
            }
        }

        PlaceCategoryAdapter placeCategoryAdapter = new PlaceCategoryAdapter(categoryList.stream().filter(item -> item.getPlace().size() > 0).collect(Collectors.toList()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewCategories.setLayoutManager(layoutManager);
        binding.recyclerViewCategories.setAdapter(placeCategoryAdapter);
    }

    private void getData() {
        PlaceDao dao = db.placeDao();
        listData = dao.getAll();
        List<Integer> ids = listData.stream()
                .map(item -> item.getPlace().getId())
                .collect(Collectors.toList());
        List<PlaceRatingAvg> listRating = db.reviewDao().getPlaceRatingAvg(ids);
        Map<Integer, PlaceWithCategory> placeMap = new HashMap<>();
        for (PlaceWithCategory item : listData) {
            placeMap.put(item.getPlace().getId(), item);
        }
        for (PlaceRatingAvg rating : listRating) {
            PlaceWithCategory item = placeMap.get(rating.getPlaceId());
            if (item != null) {
                item.getPlace().setAvgRating(rating.getAvgRating());
            }
        }

        listData.sort((a, b) -> Double.compare(b.getPlace().getAvgRating(), a.getPlace().getAvgRating()));
        List<PlaceWithCategory> top6Rating = listData.subList(0, Math.min(6, listData.size()));

        PlaceRatingAdapter adapter = new PlaceRatingAdapter(top6Rating);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.placeRating.setLayoutManager(layout);
        binding.placeRating.setAdapter(adapter);

        // Get list category with places
        getCategoryData();
    }

    @Override
    public void onResume() {
        getData();
        super.onResume();
    }
}
