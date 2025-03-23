package group3.vute.travellaos.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Models.Image;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ItemSliderBinding;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHoder>{

    private List<Image> data;

    public SliderAdapter(List<Image> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSliderBinding binding = ItemSliderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHoder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        Image img = data.get(position);
        if (img == null) return;
        CommonUtils.loadImage(holder.binding.sliderImg, img.getImage());
    }

    @Override
    public int getItemCount() {
        if (data != null) return data.size();
        return 0;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private ItemSliderBinding binding;
        public ViewHoder(@NonNull ItemSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
