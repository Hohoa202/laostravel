package group3.vute.travellaos.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Models.CategoryWithPlaces;
import group3.vute.travellaos.databinding.ItemPlaceCategoryBinding;

public class PlaceCategoryAdapter extends RecyclerView.Adapter<PlaceCategoryAdapter.viewHolder> {
    private List<CategoryWithPlaces> categoryList;

    public PlaceCategoryAdapter(List<CategoryWithPlaces> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public PlaceCategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlaceCategoryBinding binding = ItemPlaceCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceCategoryAdapter.viewHolder holder, int position) {
        CategoryWithPlaces category = categoryList.get(position);
        if (category.getPlace().size() > 0) {
            holder.bind(category);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ItemPlaceCategoryBinding binding;
        private PlaceItemAdapter adapter;

        public viewHolder(@NonNull ItemPlaceCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            adapter = new PlaceItemAdapter();
            binding.recyclerView.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
            binding.recyclerView.setAdapter(adapter);
        }

        public void bind(CategoryWithPlaces category) {
            binding.txtCategoryName.setText(category.getCategory().getName());
            adapter.setData(category.getPlace());
            adapter.notifyDataSetChanged();
        }
    }
}
