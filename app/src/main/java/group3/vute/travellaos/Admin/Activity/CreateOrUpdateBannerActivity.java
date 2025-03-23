package group3.vute.travellaos.Admin.Activity;

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
import group3.vute.travellaos.Models.Image;
import group3.vute.travellaos.Dao.ImageDao;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.Utils.RealPathUtil;
import group3.vute.travellaos.databinding.AdminActivityCreateOrUpdateBannerBinding;

public class CreateOrUpdateBannerActivity extends AppCompatActivity {
    private AdminActivityCreateOrUpdateBannerBinding binding;
    private Image currentData;
    private ImageDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdminActivityCreateOrUpdateBannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppDatabase db = AppDatabase.getInstance(this);
        dao = db.imageDao();

        Image dataEdit = (Image) getIntent().getSerializableExtra("dataEdit");
        if (dataEdit != null) {
            this.currentData = dataEdit;
            binding.title.setText("Cập nhật thông tin");
            binding.order.setText(dataEdit.getOrder() != null ? dataEdit.getOrder().toString() : "");
            CommonUtils.loadImage(binding.image, dataEdit.getImage());
        }

        // Set event
        binding.btnSave.setOnClickListener(view -> {
            Integer order = (binding.order.getText() != null && !binding.order.getText().toString().isEmpty()) ? Integer.valueOf(binding.order.getText().toString().trim()) : null;
            if (currentData != null) {
                // Update
                currentData.setOrder(order);
                this.updateData(currentData);
            } else {
                this.insertData(new Image(null, order));
            }
        });
        binding.btnClose.setOnClickListener(v -> finish());
        binding.boxImg.setOnClickListener(v -> {
            checkRequestPermissions();
            selectImage();
        });
    }

    // ================================== UPLOAD IMAGE - START =====================================
    private Uri mUri;
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
                            if (binding.image != null) {
                                binding.image.setImageBitmap(bitmap);
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

    private void updateData(Image data) {
        if (mUri != null) {
            String strRealPath = RealPathUtil.getRealPath(this, mUri);
            data.setImage(strRealPath);
        }
        try {
            this.dao.update(data);
            CommonUtils.showToast(this, "Thông tin đã được cập nhật");
            finish();
        } catch (Exception e) {
            CommonUtils.showToast(this, "Lỗi hệ thống!");
        }
    }

    private void insertData(Image data) {
        if (mUri != null) {
            String strRealPath = RealPathUtil.getRealPath(this, mUri);
            data.setImage(strRealPath);
        } else {
            CommonUtils.showToast(this, "Vui lòng chọn hình ảnh");
            return;
        }
        try {
            this.dao.insert(data);
            CommonUtils.showToast(this, "Banner đã được tạo thành công");
            finish();
        } catch (Exception e) {
            CommonUtils.showToast(this, "Lỗi hệ thống!");
        }
    }
}
