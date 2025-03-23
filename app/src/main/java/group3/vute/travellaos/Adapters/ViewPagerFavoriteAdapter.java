package group3.vute.travellaos.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import group3.vute.travellaos.Fragment.Favorite.FoodFragment;
import group3.vute.travellaos.Fragment.Favorite.PlaceFragment;

public class ViewPagerFavoriteAdapter extends FragmentStatePagerAdapter {

    public ViewPagerFavoriteAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new FoodFragment();
            default:
                return new PlaceFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Địa điểm du lịch";
        } else {
            return "Ẩm thực";
        }
    }
}
