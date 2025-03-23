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

import group3.vute.travellaos.Admin.Adapters.ContactListAdapter;
import group3.vute.travellaos.Dao.ContactDao;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.Contact;
import group3.vute.travellaos.databinding.AdminFragmentContactBinding;

public class ContactFragment extends Fragment {
    private AdminFragmentContactBinding binding;
    private ContactListAdapter adapter;
    private List<Contact> listData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = AdminFragmentContactBinding.inflate(inflater, container, false);
        listData = new ArrayList<>();
        adapter = new ContactListAdapter(listData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        getData();
        binding.searchBox.edSearch.setHint("Nhập địa chỉ email cần tìm");

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

    private void getData() {
        AppDatabase db = AppDatabase.getInstance(getContext());
        ContactDao dao = db.contactDao();
        listData = dao.getAll();
        adapter.setData(listData);
    }

    private void searchData(String newText) {
        List<Contact> categories = new ArrayList<>();
        for (Contact item : listData) {
            if (item.getEmail().toLowerCase().contains(newText.toLowerCase())) {
                categories.add(item);
            }
        }
        adapter.setData(categories);
        binding.emptyProduct.setVisibility(View.GONE);
        if (categories.isEmpty()) binding.emptyProduct.setVisibility(View.VISIBLE);
    }
}