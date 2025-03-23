package group3.vute.travellaos.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import group3.vute.travellaos.Fragment.FoodFragment;
import group3.vute.travellaos.Fragment.HomeFragment;
import group3.vute.travellaos.Fragment.OtherFragment;
import group3.vute.travellaos.Fragment.PlaceFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new PlaceFragment();
            case 2:
                return new FoodFragment();
            case 3:
                return new OtherFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
