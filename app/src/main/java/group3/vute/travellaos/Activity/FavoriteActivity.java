package group3.vute.travellaos.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import group3.vute.travellaos.Adapters.ViewPagerFavoriteAdapter;
import group3.vute.travellaos.R;
import group3.vute.travellaos.databinding.ActivityFavoriteBinding;

public class FavoriteActivity extends AppCompatActivity {
    private ActivityFavoriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.boxHeader.txtHeaderBox.setText("Bộ sưu tập yêu thích");
        binding.boxHeader.btnBack.setOnClickListener(v -> finish());

        binding.tab.setupWithViewPager(binding.viewPager.findViewById(R.id.viewPager));
        ViewPagerFavoriteAdapter viewPagerFavoriteAdapter = new ViewPagerFavoriteAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(viewPagerFavoriteAdapter);
        binding.viewPager.setOffscreenPageLimit(2);
    }
}