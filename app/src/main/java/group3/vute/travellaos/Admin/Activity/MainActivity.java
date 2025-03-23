package group3.vute.travellaos.Admin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import group3.vute.travellaos.Activity.LoginActivity;
import group3.vute.travellaos.Admin.Fragments.BannerFragment;
import group3.vute.travellaos.Admin.Fragments.ContactFragment;
import group3.vute.travellaos.Admin.Fragments.FoodCategoryFragment;
import group3.vute.travellaos.Admin.Fragments.FoodFragment;
import group3.vute.travellaos.Admin.Fragments.HomeFragment;
import group3.vute.travellaos.Admin.Fragments.PlaceCategoryFragment;
import group3.vute.travellaos.Admin.Fragments.PlaceFragment;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.AdminNavHeaderBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(group3.vute.travellaos.R.layout.admin_activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        View headerView = navigationView.getHeaderView(0);
        AdminNavHeaderBinding binding = AdminNavHeaderBinding.bind(headerView);
        User user = CommonUtils.getAuth(this);
        if (user != null) {
            binding.authFullName.setText(user.getFullName());
            binding.authEmail.setText(user.getEmail());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (itemId == R.id.nav_place_category) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlaceCategoryFragment()).commit();
        } else if (itemId == R.id.nav_place) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlaceFragment()).commit();
        } else if (itemId == R.id.nav_food) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FoodFragment()).commit();
        } else if (itemId == R.id.nav_food_category) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FoodCategoryFragment()).commit();
        } else if (itemId == R.id.nav_banner) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BannerFragment()).commit();
        } else if (itemId == R.id.nav_contact) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactFragment()).commit();
        } else if (itemId == R.id.nav_logout) {
            CommonUtils.authLogout(MainActivity.this);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (itemId == R.id.nav_back) {
            startActivity(new Intent(MainActivity.this, group3.vute.travellaos.Activity.MainActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}