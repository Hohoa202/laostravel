package group3.vute.travellaos.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import group3.vute.travellaos.Adapters.FoodItemSearchAdapter;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Dao.FoodDao;
import group3.vute.travellaos.Models.FoodWithCategory;
import group3.vute.travellaos.R;
import group3.vute.travellaos.databinding.ActivitySearchFoodBinding;

public class SearchFoodActivity extends AppCompatActivity {
    private ActivitySearchFoodBinding binding;
    private FoodItemSearchAdapter adapter;
    private List<FoodWithCategory> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        binding = ActivitySearchFoodBinding.inflate(getLayoutInflater());
        adapter = new FoodItemSearchAdapter();
        adapter.setData(listData);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        binding.listData.setLayoutManager(layout);
        binding.listData.setAdapter(adapter);

        getData();
        binding.btnCancel.setOnClickListener(v -> finish());
        binding.search.edSearch.addTextChangedListener(new TextWatcher() {
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
        setContentView(binding.getRoot());
    }

    private void searchData(String newText) {
        List<FoodWithCategory> data = new ArrayList<>();
        for (FoodWithCategory item : listData) {
            if (item.getFood().getName().toLowerCase().contains(newText.toLowerCase())
                    || item.getFood().getOrigin().toLowerCase().contains(newText.toLowerCase())) {
                data.add(item);
            }
        }
        adapter.setData(data);
        binding.empty.setVisibility(View.GONE);
        if (data.isEmpty()) binding.empty.setVisibility(View.VISIBLE);
    }

    private void getData() {
        AppDatabase db = AppDatabase.getInstance(this);
        FoodDao dao = db.foodDao();
        listData = dao.getAll();
        adapter.setData(listData);
    }
}