package group3.vute.travellaos.Admin.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import group3.vute.travellaos.Admin.Activity.CreateOrUpdateBannerActivity;
import group3.vute.travellaos.Admin.Adapters.BannerListAdapter;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.Image;
import group3.vute.travellaos.Dao.ImageDao;
import group3.vute.travellaos.databinding.AdminFragmentBannerBinding;

public class BannerFragment extends Fragment {
    private BannerListAdapter adapter;
    private List<Image> listData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AdminFragmentBannerBinding binding = AdminFragmentBannerBinding.inflate(inflater, container, false);
        listData = new ArrayList<>();
        adapter = new BannerListAdapter(listData);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.btnAdd.setOnClickListener(v -> startActivity(new Intent(getContext(), CreateOrUpdateBannerActivity.class)));
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        AppDatabase db = AppDatabase.getInstance(getActivity());
        ImageDao dao = db.imageDao();
        listData = dao.getAll();
        adapter.setData(listData);
    }
}