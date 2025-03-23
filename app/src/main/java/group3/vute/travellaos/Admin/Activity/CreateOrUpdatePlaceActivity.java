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
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.Place;
import group3.vute.travellaos.Models.PlaceCategory;
import group3.vute.travellaos.Dao.PlaceCategoryDao;
import group3.vute.travellaos.Dao.PlaceDao;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.Utils.RealPathUtil;
import group3.vute.travellaos.databinding.AdminActivityCreateOrUpdatePlaceBinding;
import okhttp3.MultipartBody;

public class CreateOrUpdatePlaceActivity extends AppCompatActivity {
    private AdminActivityCreateOrUpdatePlaceBinding binding;
    private Place currentData;
    private List<PlaceCategory> listCategories;
    private AppDatabase db;
    private PlaceDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdminActivityCreateOrUpdatePlaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getInstance(this);
        dao = db.placeDao();

        Place place = (Place) getIntent().getSerializableExtra("dataEdit");
        if (place != null) {
            this.currentData = place;
            binding.title.setText("Cập nhật thông tin");
            binding.inputPlaceName.setText(place.getName());
            binding.inputAddress.setText(place.getAddress());
            binding.inputDescription.setText(place.getDescription());
            CommonUtils.loadImage(binding.imgProduct, place.getImage());
        }
        getCategories();

        // Set event
        binding.btnSave.setOnClickListener(view -> {
            PlaceCategory category = listCategories.get(binding.spinnerCategory.getSelectedItemPosition());
            String name = (binding.inputPlaceName.getText() != null) ? binding.inputPlaceName.getText().toString().trim() : "";
            String address = (binding.inputAddress.getText() != null) ? binding.inputAddress.getText().toString().trim() : "";
            binding.productNameLayout.setHelperText("");
            if (name.isEmpty()) {
                binding.productNameLayout.setHelperText("Dữ liệu bắt buộc nhập");
            } else if (address.isEmpty()) {
                binding.AddressLayout.setHelperText("Dữ liệu bắt buộc nhập");
            } else {
                String description = (binding.inputDescription.getText() != null) ? binding.inputDescription.getText().toString().trim() : "";

                if (currentData != null) {
                    // Update
                    currentData.setCategoryId(category.getId());
                    currentData.setName(name);
                    currentData.setAddress(address);
                    currentData.setDescription(description);
                    this.updateData(currentData);
                } else {
                    this.insertData(new Place(name, category.getId(), description, address, null));
                }
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
                            if (binding.imgProduct != null) {
                                binding.imgProduct.setImageBitmap(bitmap);
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
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                CommonUtils.showToast(this, "Permission denied");
            }
        }
    }
    // ========================================= END ===============================================

    private void updateData(Place data) {
        MultipartBody.Part multipartBodyThumb = null;
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

    private void insertData(Place data) {
        if (mUri != null) {
            String strRealPath = RealPathUtil.getRealPath(this, mUri);
            data.setImage(strRealPath);
        }
        try {
            this.dao.insert(data);
            CommonUtils.showToast(this, "Địa điểm đã được tạo thành công");
            finish();
        } catch (Exception e) {
            CommonUtils.showToast(this, "Lỗi hệ thống!");
        }
    }


    /**
     * Call API get categories and set into spinner
     */
    private void getCategories() {
        PlaceCategoryDao categoryDao = db.placeCategoryDao();
        listCategories = categoryDao.getAll();
        List<String> categoryNames = new ArrayList<>();
        int position = -1;
        for (int i = 0; i < listCategories.size(); i++) {
            PlaceCategory category = listCategories.get(i);
            categoryNames.add(category.getName());
            if (currentData != null && category.getId() == currentData.getCategoryId()) {
                position = i;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateOrUpdatePlaceActivity.this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerCategory.setAdapter(adapter);
        binding.spinnerCategory.setSelection(position);
    }

    // Change toppings
//    @Override
//    public void onToppingSelected(List<Topping> listTopping) {
//        this.listToppingSelected = listTopping;
//    }
}
