package group3.vute.travellaos.Fragment.Favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import group3.vute.travellaos.Adapters.FavoriteFoodAdapter;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Dao.FavoriteDao;
import group3.vute.travellaos.Models.FavoriteWithFood;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.FragmentFavoriteBinding;

public class FoodFragment extends Fragment {
    private FragmentFavoriteBinding binding;
    private FavoriteFoodAdapter adapter;
    private List<FavoriteWithFood> listData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listData = new ArrayList<>();
        adapter = new FavoriteFoodAdapter(null);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerView.setAdapter(adapter);

        getData();
    }

    private void getData() {
        User authLogin = CommonUtils.getAuth(getContext());
        if (authLogin == null) {
            CommonUtils.showToast(getContext(), "Vui lòng đăng nhập!");
            return;
        }

        AppDatabase db = AppDatabase.getInstance(getContext());
        FavoriteDao dao = db.favoriteDao();
        listData = dao.getWithFood(authLogin.getId());
        adapter.setData(listData);
    }
}
