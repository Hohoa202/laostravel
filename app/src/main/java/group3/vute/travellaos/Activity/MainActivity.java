package group3.vute.travellaos.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Calendar;

import group3.vute.travellaos.Adapters.ViewPagerAdapter;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setData();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        binding.mainViewPager.setAdapter(viewPagerAdapter);
        binding.mainViewPager.setUserInputEnabled(false);
        binding.mainViewPager.setOffscreenPageLimit(1);

        // Set event
        binding.btnFavorite.setOnClickListener(v -> startActivity(new Intent(this, FavoriteActivity.class)));
        binding.mainBottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            final int position;
            if (id == R.id.nav_menu_place) {
                position = 1;
            } else if (id == R.id.nav_menu_food) {
                position = 2;
            } else if (id == R.id.nav_menu_other) {
                position = 3;
            } else {
                position = 0;
            }
            binding.mainViewPager.setCurrentItem(position, false);
            return true;
        });
        binding.mainViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch (position) {
                    case 0:
                        binding.mainBottomNavigation.setSelectedItemId(R.id.nav_menu_home);
                        break;
                    case 1:
                        binding.mainBottomNavigation.setSelectedItemId(R.id.nav_menu_place);
                        break;
                    case 2:
                        binding.mainBottomNavigation.setSelectedItemId(R.id.nav_menu_food);
                        break;
                    case 3:
                        binding.mainBottomNavigation.setSelectedItemId(R.id.nav_menu_other);
                        break;
                }
            }
        });
        int fragmentIndex = getIntent().getIntExtra("fragmentIndex", 0);
        binding.mainViewPager.setCurrentItem(fragmentIndex);

        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        setData();
        super.onResume();
    }

    private void setData() {
        String greeting;
        User authUser = CommonUtils.getAuth(this);
        if (authUser != null) {
            CommonUtils.loadImage(binding.avatar, authUser.getAvatar());
            binding.avatar.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        } else {
            binding.avatar.setVisibility(View.GONE);
        }
        String fullName = authUser == null ? null : authUser.getFullName();

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String lastName = "";
        if (fullName != null) {
            String[] parts = fullName.split(" ");
            lastName = parts[parts.length - 1];
        }

        if (hour >= 6 && hour < 10) {
            greeting = fullName != null ? "Chào buổi sáng, " + lastName : "Chào buổi sáng";
        } else if (hour >= 10 && hour < 13) {
            greeting = fullName != null ? "Chào buổi trưa, " + lastName : "Chào buổi trưa";
        } else if (hour >= 13 && hour < 18) {
            greeting = fullName != null ? "Chào buổi chiều, " + lastName : "Chào buổi chiều";
        } else {
            greeting = fullName != null ? "Chúc ngủ ngon zzZZ, " + lastName : "Chúc ngủ ngon zzZZ";
        }

        binding.mainTxtHello.setText(greeting);
    }
}