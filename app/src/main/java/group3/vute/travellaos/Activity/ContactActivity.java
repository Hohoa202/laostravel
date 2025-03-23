package group3.vute.travellaos.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.Contact;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.ActivityContactBinding;

public class ContactActivity extends AppCompatActivity {
    private ActivityContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        binding.btnBack.setOnClickListener(v -> finish());

        User user = CommonUtils.getAuth(this);
        Integer userId;
        if (user != null) {
            userId = user.getId();
            binding.email.setText(user.getEmail());
            binding.email.setClickable(false);
            binding.email.setFocusable(false);
            binding.email.setBackgroundResource(R.drawable.bgr_edit_text_readonly);
        } else {
            userId = null;
        }

        binding.btnSubmit.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String content = binding.content.getText().toString();

            if (email.isEmpty()) {
                CommonUtils.showToast(this, "Vui lòng nhập email của bạn");
                return;
            }
            if (!CommonUtils.isValidEmail(email)) {
                CommonUtils.showToast(this, "Email sai định dạng");
                return;
            }
            if (content.isEmpty()) {
                CommonUtils.showToast(this, "Vui lòng nhập nội dung liên hệ");
                return;
            }

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            AppDatabase db = AppDatabase.getInstance(this);
            try {
                db.contactDao().insert(new Contact(userId, sdf.format(new Date()), email, content));
                binding.email.setText("");
                binding.content.setText("");
                CommonUtils.showToast(this, "Gửi liên hệ thành công");
            } catch (Exception e) {
                CommonUtils.showToast(this, "Đã có lỗi xẩy ra!");
            }
        });
        setContentView(binding.getRoot());
    }
}