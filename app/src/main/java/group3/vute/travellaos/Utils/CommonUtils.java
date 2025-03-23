package group3.vute.travellaos.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import group3.vute.travellaos.Dao.FavoriteDao;
import group3.vute.travellaos.Dialog.LoadingDialog;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.Favorite;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.R;
import group3.vute.travellaos.databinding.AdminAlertConfirmBinding;

public class CommonUtils extends AppCompatActivity {
    private static Toast currentToast;
    private static LoadingDialog loadingDialog;


    /**
     * Show toast
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        if (currentToast != null)
            currentToast.cancel();
        if (context != null) {
            currentToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            currentToast.show();
        }
    }

    /**
     * Show image url up imageView
     *
     * @param imageView
     * @param imgUrl
     * @return void
     */
    public static void loadImage(ImageView imageView, String imgUrl) {
        if (imgUrl != null && !imgUrl.isEmpty()) {
            File imgFile = new File(imgUrl);
            Picasso.get().load(imgFile).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.img_no_image);
        }
    }

    /**
     * Convert Date string to Date type
     *
     * @param stringDate
     * @return
     */
    public static Date convertDate(String stringDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }


    /**
     * format Date Time string
     *
     * @param dateStr
     * @return
     */
    public static String formatStrDateTime(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "";
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault());

        try {
            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
    }

    /**
     * format Date string
     *
     * @param dateStr
     * @return
     */
    public static String formatStrDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "";
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
    }

    /**
     * Get information of user login
     *
     * @param context
     * @return User|null
     */
    public static User getAuth(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("user_id", -1);
        if (id == -1) return null;

        User user = new User(
                sharedPreferences.getString("user_fullName", null),
                sharedPreferences.getString("user_email", null),
                sharedPreferences.getString("user_phone", null),
                sharedPreferences.getString("user_avatar", null),
                sharedPreferences.getInt("user_role", 0)
        );
        user.setId(sharedPreferences.getInt("user_id", 0));
        return user;
    }

    /**
     * Update auth info
     *
     * @param context
     * @param data
     */
    public static void updateAuth(Context context, User data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_id", data.getId());
        editor.putInt("user_role", data.getRole());
        editor.putString("user_fullName", data.getFullName());
        editor.putString("user_email", data.getEmail());
        editor.putString("user_phone", data.getPhone());
        editor.putString("user_address", data.getAddress());
        editor.putString("user_avatar", data.getAvatar());
        editor.apply();
    }

    /**
     * Logout Auth clear SharedPreferences info
     *
     * @param context
     */
    public static void authLogout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Toggle loading dialog
     *
     * @param context
     */
    public static void toggleLoadingDialog(Context context) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        } else {
            loadingDialog = new LoadingDialog(context);
            loadingDialog.show();
        }
    }


    /**
     * Interface call back confirm
     */
    public interface ConfirmationDialogListener {
        void onConfirm();
    }

    /**
     * Show confirm alert dialog
     *
     * @param context
     * @param title
     * @param message
     * @param listener
     */
    public static void showConfirmDialog(Context context, String title, String message, final ConfirmationDialogListener listener) {
        AdminAlertConfirmBinding binding = AdminAlertConfirmBinding.inflate(LayoutInflater.from(context));
        binding.title.setText(title);
        binding.message.setText(message);

        AlertDialog dialog = new AlertDialog.Builder(context).setView(binding.getRoot()).create();
        dialog.show();

        binding.btnCancel.setOnClickListener(v -> dialog.dismiss());
        binding.btnClose.setOnClickListener(v -> dialog.dismiss());

        binding.btnConfirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onConfirm();
            }
            dialog.dismiss();
        });
    }

    /**
     * Get Activity By Context
     *
     * @param context
     * @return
     */
    public static Activity getActivityByContext(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) context;
    }

    public static void addPlaceToFavorite(Context context, int placeId) {
        User user = getAuth(context);
        if (user == null) {
            showToast(context, "Vui lòng đăng nhập để sử dụng chức năng");
            return;
        }
        AppDatabase db = AppDatabase.getInstance(context);
        FavoriteDao dao = db.favoriteDao();

        boolean checkExist = dao.checkPlaceExist(placeId, user.getId());
        if (!checkExist) {
            try {
                dao.insert(new Favorite(user.getId(), placeId, null));
            } catch (Exception e) {
                Toast.makeText(context, "Đã có lỗi xẩy ra!", Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(context, "Đã thêm vào bộ sưu tập yêu thích", Toast.LENGTH_SHORT).show();
    }

    public static void addFoodToFavorite(Context context, int foodId) {
        User user = getAuth(context);
        if (user == null) {
            showToast(context, "Vui lòng đăng nhập để sử dụng chức năng");
            return;
        }
        AppDatabase db = AppDatabase.getInstance(context);
        FavoriteDao dao = db.favoriteDao();

        boolean checkExist = dao.checkFoodExist(foodId, user.getId());
        if (!checkExist) {
            try {
                dao.insert(new Favorite(user.getId(), null, foodId));
            } catch (Exception e) {
                Toast.makeText(context, "Đã có lỗi xẩy ra!", Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(context, "Đã thêm vào bộ sưu tập yêu thích", Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(regex);
    }
}
