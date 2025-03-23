package group3.vute.travellaos.Admin.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import group3.vute.travellaos.Models.Contact;
import group3.vute.travellaos.R;
import group3.vute.travellaos.databinding.AdminItemContactListBinding;

public class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Contact> listData;

    public ContactListAdapter(List<Contact> listData) {
        this.listData = listData;
    }

    public void setData(List<Contact> data) {
        this.listData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminItemContactListBinding binding = AdminItemContactListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Contact item = listData.get(position);
        if (item == null) return;
        ContactListAdapter.ViewHolder viewHolder = (ContactListAdapter.ViewHolder) holder;
        viewHolder.binding.email.setText(item.getEmail());
        viewHolder.binding.createdAt.setText(item.getCreatedAt());
        viewHolder.binding.content.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        if (listData != null) return listData.size();
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AdminItemContactListBinding binding;

        public ViewHolder(@NonNull AdminItemContactListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.item.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    int visibility = binding.boxDetail.getVisibility();
                    if (visibility == View.VISIBLE) {
                        binding.boxDetail.setVisibility(View.GONE);
                        binding.createdAt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_24, 0);
                    } else {
                        binding.boxDetail.setVisibility(View.VISIBLE);
                        binding.createdAt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_24, 0);
                    }
                }
            });
        }
    }
}
