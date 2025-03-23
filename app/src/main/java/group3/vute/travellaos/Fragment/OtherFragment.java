package group3.vute.travellaos.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import group3.vute.travellaos.Activity.ContactActivity;
import group3.vute.travellaos.Activity.LoginActivity;
import group3.vute.travellaos.Activity.ProfileActivity;
import group3.vute.travellaos.Admin.Activity.MainActivity;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.FragmentOtherBinding;

public class OtherFragment extends Fragment {
    private FragmentOtherBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOtherBinding.inflate(inflater, container, false);

        User user = CommonUtils.getAuth(getContext());

        binding.btnAdmin.setOnClickListener(v -> {
            if (user == null || user.getRole() == 0) {
                CommonUtils.showToast(getContext(), "Vui lòng đăng nhập bằng tài khoản quản trị!");
                CommonUtils.authLogout(getContext());
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        binding.btnProfile.setOnClickListener(v -> {
            if (user != null) {
                v.getContext().startActivity(new Intent(v.getContext(), ProfileActivity.class));
            } else {
                CommonUtils.showToast(getContext(), "Vui lòng đăng nhập");
            }
        });

        binding.btnContact.setOnClickListener(v -> startActivity(new Intent(getContext(), ContactActivity.class)));

        // Set event
        if (user != null) {
            binding.btnLogout.setOnClickListener(v -> {
                CommonUtils.authLogout(v.getContext());
                v.getContext().startActivity(new Intent(v.getContext(), LoginActivity.class));
            });
        } else {
            binding.btnLogout.setText("Đăng nhập");
            binding.btnLogout.setOnClickListener(v -> {
                v.getContext().startActivity(new Intent(v.getContext(), LoginActivity.class));
            });
        }

        return binding.getRoot();
    }
}
