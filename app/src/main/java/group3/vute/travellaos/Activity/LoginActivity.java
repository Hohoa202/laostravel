package group3.vute.travellaos.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.Dao.UserDao;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppDatabase db = AppDatabase.getInstance(this);
        userDao = db.userDao();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Start register activity
        binding.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        // Click login
        binding.btnHandleLogin.setOnClickListener(v -> {
            String email = binding.loginEdUsername.getText().toString().trim();
            String password = binding.loginEdPassword.getText().toString().trim();
            this.handleLogin(email, password);
        });

        // Click back home page
        binding.btnBackHome.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private boolean validate(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Vui lòng nhập số điện thoại và mật khẩu đăng nhập", Toast.LENGTH_SHORT).show();
            return false;
        }
        binding.loginEdUsername.requestFocus();
        return true;
    }

    private void handleLogin(String email, String password) {
        if (!validate(email, password)) return;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            User user = userDao.loginUser(email, password);
            runOnUiThread(() -> {
                if (user != null) {
                    CommonUtils.updateAuth(this, user);
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    if (user.getRole() == 1) { // Role 1: ADMIN
                        startActivity(new Intent(this, group3.vute.travellaos.Admin.Activity.MainActivity.class));
                    } else {
                        startActivity(new Intent(this, MainActivity.class));
                    }
                } else {
                    Toast.makeText(this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    binding.loginEdPassword.requestFocus();
                }
            });
        });
    }
}