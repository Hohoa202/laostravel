package group3.vute.travellaos.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import group3.vute.travellaos.Dao.UserDao;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AppDatabase db = AppDatabase.getInstance(this);
        UserDao userDao = db.userDao();

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

        // Handle submit register
        binding.btnSubmit.setOnClickListener(v -> {
            boolean check = true;
            String fullName = binding.registerFullName.getText().toString().trim();
            String phone = binding.registerPhone.getText().toString().trim();
            String email = binding.registerEmail.getText().toString().trim();
            String password = binding.registerPassword.getText().toString().trim();
            String cfPassword = binding.registerCfPassword.getText().toString().trim();

            binding.lbFullName.setVisibility(View.GONE);
            binding.lbPass.setVisibility(View.GONE);
            binding.lbPhone.setVisibility(View.GONE);
            binding.lbCfPass.setVisibility(View.GONE);
            binding.lbEmail.setVisibility(View.GONE);

            if (fullName.isEmpty()) {
                binding.lbFullName.setVisibility(View.VISIBLE);
                binding.lbFullName.setText(" * Họ và tên không được để trống");
                check = false;
            } else if (fullName.length() > 50) {
                binding.lbFullName.setVisibility(View.VISIBLE);
                binding.lbFullName.setText(" * Họ và tên tối đa 50 ký tự");
                check = false;
            }

            if (phone.length() < 10 || phone.length() > 11) {
                binding.lbPhone.setVisibility(View.VISIBLE);
                binding.lbPhone.setText(" * Số điện thoại 10 đến 11 ký tự");
                check = false;
            }

            if (email.isEmpty()) {
                binding.lbEmail.setVisibility(View.VISIBLE);
                binding.lbEmail.setText(" * Email không được để trống");
                check = false;
            } else if (email.length() > 50) {
                binding.lbEmail.setVisibility(View.VISIBLE);
                binding.lbEmail.setText(" * Email tối đa 100 ký tự");
                check = false;
            } else if (!CommonUtils.isValidEmail(email)) {
                binding.lbEmail.setVisibility(View.VISIBLE);
                binding.lbEmail.setText(" * Email không đúng định dạng");
                check = false;
            } else if (userDao.checkEmail(email)) {
                binding.lbEmail.setVisibility(View.VISIBLE);
                binding.lbEmail.setText(" * Email đã tồn tại trong hệ thống");
                return;
            }

            if (password.length() < 6) {
                binding.lbPass.setVisibility(View.VISIBLE);
                binding.lbPass.setText(" * Mật khẩu tối thiểu 6 ký tự");
                check = false;
            }

            if (!password.equals(cfPassword)) {
                binding.lbCfPass.setVisibility(View.VISIBLE);
                binding.lbCfPass.setText(" * Xác nhận mật khẩu không trùng khớp");
                check = false;
            }

            if (!check) return;
            try {
                userDao.insertUser(new User(fullName, email, phone, password));
                CommonUtils.showToast(this, "Đăng ký thành công");
                startActivity(new Intent(this, LoginActivity.class));
            } catch (Exception e) {
                Log.e("Register Fail: ", e.toString());
                CommonUtils.showToast(this, "Đăng ký thất bại! Đã có lỗi xảy ra");
            }
        });
    }
}