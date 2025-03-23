package group3.vute.travellaos.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import group3.vute.travellaos.Activity.LoginActivity;
import group3.vute.travellaos.Adapters.ReviewAdapter;
import group3.vute.travellaos.Models.AppDatabase;
import group3.vute.travellaos.Models.Food;
import group3.vute.travellaos.Models.Review;
import group3.vute.travellaos.Models.ReviewWithUser;
import group3.vute.travellaos.Models.User;
import group3.vute.travellaos.R;
import group3.vute.travellaos.Utils.CommonUtils;
import group3.vute.travellaos.databinding.DialogFoodDetailsBinding;

public class FoodDetailDialog extends Dialog {
    private final DialogFoodDetailsBinding binding;
    private List<ReviewWithUser> listReview;

    @SuppressLint("SetTextI18n")
    public FoodDetailDialog(@NonNull Context context, Food data) {
        super(context);
        binding = DialogFoodDetailsBinding.inflate(LayoutInflater.from(context));
        AppDatabase db = AppDatabase.getInstance(getContext());

        // Load review data
        listReview = db.reviewDao().getByFoodId(data.getId());
        ReviewAdapter adapter = new ReviewAdapter(listReview);
        binding.listReview.setVisibility(listReview.size() > 0 ? View.VISIBLE : View.GONE);
        binding.boxEmpty.setVisibility(listReview.size() > 0 ? View.GONE : View.VISIBLE);
        binding.listReview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listReview.setAdapter(adapter);

        // Set box review
        binding.txtTitle.setText(data.getName());
        binding.txtOrigin.setText(data.getOrigin());
        binding.txtContent.setText(data.getDescription());
        CommonUtils.loadImage(binding.pic, data.getImage());

        // Check user review -> set data rating
        User user = CommonUtils.getAuth(getContext());
        if (user == null) {
            binding.boxLogin.setVisibility(View.VISIBLE);
            binding.boxReview.setVisibility(View.GONE);
        } else {
            binding.boxLogin.setVisibility(View.GONE);
            binding.boxReview.setVisibility(View.VISIBLE);
            binding.fullName.setText(user.getFullName());
            CommonUtils.loadImage(binding.avatar, user.getAvatar());

            Review dataCheck = db.reviewDao().getReviewedFood(user.getId(), data.getId());
            if (dataCheck != null) {
                binding.editTextContent.setText(dataCheck.getContent());
                binding.editTextContent.setEnabled(false);
                binding.btnSend.setVisibility(View.GONE);
                binding.ratingBar.setRating((float) dataCheck.getRating());
                binding.ratingBar.setIsIndicator(true);
            } else {
                binding.btnSend.setVisibility(View.VISIBLE);
                binding.btnSend.setOnClickListener(v -> {
                    String content = binding.editTextContent.getText().toString();
                    if (content.isEmpty()) {
                        CommonUtils.showToast(getContext(), "Vui lòng nhập nội dung đánh giá");
                        return;
                    }
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    try {
                        db.reviewDao().insert(new Review(user.getId(), null, data.getId(), (int) binding.ratingBar.getRating(), content, sdf.format(new Date())));
                        CommonUtils.showToast(getContext(), "Đánh giá thành công");
                        listReview = db.reviewDao().getByFoodId(data.getId());
                        adapter.setData(listReview);

                        binding.boxEmpty.setVisibility(View.GONE);
                        binding.listReview.setVisibility(View.VISIBLE);
                        binding.btnSend.setVisibility(View.GONE);
                        binding.ratingBar.setIsIndicator(true);
                        binding.editTextContent.setEnabled(false);
                        binding.editTextContent.setFocusable(false);
                    } catch (Exception e) {
                        CommonUtils.showToast(getContext(), "Đã có lỗi xẩy ra");
                    }
                });
            }
        }

        // Config ratingBar
        binding.ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (rating < 1) {
                ratingBar1.setRating(1);
            }
        });

        // Set event
        binding.btnClose.setOnClickListener(v -> dismiss());
        binding.btnLogin.setOnClickListener(v -> getContext().startActivity(new Intent(getContext(), LoginActivity.class)));
        binding.btnShowDetail.setOnClickListener(v -> {
            int visibility = binding.boxListReview.getVisibility();
            if (visibility == View.VISIBLE) {
                binding.boxListReview.setVisibility(View.GONE);
                binding.btnShowDetail.setText("Xem đánh giá");
                binding.btnShowDetail.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_arrow_down_24);
            } else {
                binding.boxListReview.setVisibility(View.VISIBLE);
                binding.btnShowDetail.setText("Ẩn đánh giá");
                binding.btnShowDetail.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_arrow_up_24);
            }
        });

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getWindow().setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
    }
}
