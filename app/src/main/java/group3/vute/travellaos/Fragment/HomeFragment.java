package group3.vute.travellaos.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import group3.vute.travellaos.Activity.LoginActivity;
import group3.vute.travellaos.Activity.RegisterActivity;
import group3.vute.travellaos.Activity.SearchFoodActivity;
import group3.vute.travellaos.Adapters.FoodNewAdapter;
import group3.vute.travellaos.Adapters.PlaceNewAdapter;
import group3.vute.travellaos.Adapters.SliderAdapter;
import group3.vute.travellaos.Dao.FoodDao;
import group3.vute.travellaos.Dao.ImageDao;
import group3.vute.travellaos.Dao.PlaceDao;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.FoodRatingAvg;
import group3.vute.travellaos.Models.FoodWithCategory;
import group3.vute.travellaos.Models.Image;
import group3.vute.travellaos.Models.PlaceRatingAvg;
import group3.vute.travellaos.Models.PlaceWithCategory;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.Transformer.ZoomOutPageTransformer;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private List<PlaceWithCategory> listPlaces;
    private List<FoodWithCategory> listFoods;
    private List<Image> listSlides;
    private AppDatabase db;
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (listSlides != null) {
                if (binding.homeSlider.getCurrentItem() == listSlides.size() - 1)
                    binding.homeSlider.setCurrentItem(0);
                else
                    binding.homeSlider.setCurrentItem(binding.homeSlider.getCurrentItem() + 1);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        db = AppDatabase.getInstance(getContext());

        // Set event
        // binding.btnOrder.setOnClickListener(v -> startActivity(new Intent(getContext(), OrderHistoryActivity.class)));
        binding.search.edSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                startActivity(new Intent(getContext(), SearchFoodActivity.class));
            }
        });

        binding.search.edSearch.setOnClickListener(v -> startActivity(new Intent(getContext(), SearchFoodActivity.class)));

        // Set data
        User authUser = CommonUtils.getAuth(getContext());
        if (authUser == null) {
            binding.boxLogin.setVisibility(View.VISIBLE);
            binding.btnLogin.setOnClickListener(v ->
                    v.getContext().startActivity(new Intent(v.getContext(), LoginActivity.class))
            );
            binding.btnRegister.setOnClickListener(v ->
                    v.getContext().startActivity(new Intent(v.getContext(), RegisterActivity.class))
            );
        }
        getData();

        return binding.getRoot();
    }

    private void getData() {
        // Place new
        PlaceDao placeDao = db.placeDao();
        listPlaces = placeDao.getNew();
        List<Integer> placeIds = listPlaces.stream()
                .map(place -> place.getPlace().getId())
                .collect(Collectors.toList());
        List<PlaceRatingAvg> listRating = db.reviewDao().getPlaceRatingAvg(placeIds);
        Map<Integer, PlaceWithCategory> placeMap = new HashMap<>();
        for (PlaceWithCategory place : listPlaces) {
            placeMap.put(place.getPlace().getId(), place);
        }
        for (PlaceRatingAvg rating : listRating) {
            PlaceWithCategory place = placeMap.get(rating.getPlaceId());
            if (place != null) {
                place.getPlace().setAvgRating(rating.getAvgRating());
            }
        }

        PlaceNewAdapter placeItemAdapter = new PlaceNewAdapter(listPlaces);
        binding.recyclerviewPlace.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerviewPlace.setAdapter(placeItemAdapter);

        // Food new
        FoodDao foodDao = db.foodDao();
        listFoods = foodDao.getNew();
        List<Integer> foodIds = listFoods.stream()
                .map(place -> place.getFood().getId())
                .collect(Collectors.toList());
        List<FoodRatingAvg> listFoodRating = db.reviewDao().getFoodRatingAvg(foodIds);
        Map<Integer, FoodWithCategory> foodMap = new HashMap<>();
        for (FoodWithCategory food : listFoods) {
            foodMap.put(food.getFood().getId(), food);
        }
        for (FoodRatingAvg rating : listFoodRating) {
            FoodWithCategory food = foodMap.get(rating.getFoodId());
            if (food != null) {
                food.getFood().setAvgRating(rating.getAvgRating());
            }
        }
        FoodNewAdapter foodNewAdapter = new FoodNewAdapter(listFoods);
        binding.recyclerviewFood.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerviewFood.setAdapter(foodNewAdapter);

        // Slider
        ImageDao imageDao = db.imageDao();
        listSlides = imageDao.getAll();
        if (listSlides.size() == 0) binding.homeSlider.setVisibility(View.GONE);
        SliderAdapter adapter = new SliderAdapter(listSlides);
        binding.homeSlider.setAdapter(adapter);
        binding.homeSlider.setPageTransformer(new ZoomOutPageTransformer());
        binding.homeSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
        binding.homeSliderDot.setViewPager(binding.homeSlider);
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void onResume() {
        getData();
        handler.postDelayed(runnable, 3000);
        super.onResume();
    }
}


//        binding.editor.setEditorHeight(200);
//        binding.editor.setEditorFontSize(16);
//        binding.editor.setPlaceholder("Nhập nội dung...");
//
//        binding.btnBold.setOnClickListener(v -> binding.editor.setBold());
//
//        binding.btnItalic.setOnClickListener(v -> binding.editor.setItalic());
//
//        binding.btnUnderline.setOnClickListener(v -> binding.editor.setUnderline());