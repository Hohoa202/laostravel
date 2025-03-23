package group3.vute.travellaos.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Models.CategoryWithFoods;
import group3.vute.travellaos.Models.CategoryWithPlaces;
import group3.vute.travellaos.databinding.ItemFoodCategoryBinding;
import group3.vute.travellaos.databinding.ItemPlaceCategoryBinding;

public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.viewHolder> {
    private List<CategoryWithFoods> categoryList;

    public FoodCategoryAdapter(List<CategoryWithFoods> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public FoodCategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodCategoryBinding binding = ItemFoodCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodCategoryAdapter.viewHolder holder, int position) {
        CategoryWithFoods category = categoryList.get(position);
        if (!category.getFood().isEmpty()) {
            holder.bind(category);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ItemFoodCategoryBinding binding;
        private FoodItemAdapter adapter;

        public viewHolder(@NonNull ItemFoodCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            adapter = new FoodItemAdapter();
            binding.recyclerView.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
            binding.recyclerView.setAdapter(adapter);
        }

        @SuppressLint("NotifyDataSetChanged")
        public void bind(CategoryWithFoods category) {
            binding.txtCategoryName.setText(category.getCategory().getName());
            adapter.setData(category.getFood());
            adapter.notifyDataSetChanged();
        }
    }
}
