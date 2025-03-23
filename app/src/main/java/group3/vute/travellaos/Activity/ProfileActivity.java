package group3.vute.travellaos.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.Utils.RealPathUtil;
import group3.vute.travellaos.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private AppDatabase db;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        db = AppDatabase.getInstance(this);

        // Set data
        User userLogin = CommonUtils.getAuth(this);

        if (userLogin != null) {
            User findUser = db.userDao().find(userLogin.getId());

            CommonUtils.loadImage(binding.imgUser, findUser.getAvatar());
            binding.updateFullName.setText(findUser.getFullName());
            binding.updateEmail.setText(findUser.getEmail());
            binding.updatePhone.setText(findUser.getPhone());
            binding.updateAddress.setText(findUser.getAddress());
            binding.btnUpdate.setOnClickListener(v -> handleUpdateProfile(findUser));
        }
        binding.cardView.setOnClickListener(v -> {
            checkRequestPermissions();
            selectImage();
        });
        binding.btnClose.setOnClickListener(v -> finish());
        setContentView(binding.getRoot());
    }

    /**
     * Handle update profile
     */
    private void handleUpdateProfile(User user) {
        if (mUri != null) {
            String strRealPath = RealPathUtil.getRealPath(this, mUri);
            user.setAvatar(strRealPath);
        }

        String fullName = binding.updateFullName.getText().toString().trim();
        String address = binding.updateAddress.getText().toString().trim();

        user.setAddress(address);
        user.setFullName(fullName);

        try {
            db.userDao().update(user);
            CommonUtils.updateAuth(this, user);
            CommonUtils.showToast(this, "Cập nhật thông tin thành công");
        } catch (Exception e) {
            CommonUtils.showToast(this, "Thất bại! Đã có lỗi xẩy ra :(");
        }
    }

    // ================================== UPLOAD IMAGE - START =====================================
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        if (data == null) return;
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            if (binding.imgUser != null) {
                                binding.imgUser.setImageBitmap(bitmap);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    /**
     * Check quyền truy cập bộ nhớ ngoài
     */
    private void checkRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
            }
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        mActivityResultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                CommonUtils.showToast(this, "Permission denied");
            }
        }
    }
    // ========================================= END ===============================================
}