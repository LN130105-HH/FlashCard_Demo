package com.crammr.studyflashcard.ui.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.NotificationItem;
import com.crammr.studyflashcard.data.repository.NotificationRepository;
import com.crammr.studyflashcard.data.repository.UserRepository;
import com.crammr.studyflashcard.databinding.ActivityForgotPasswordBinding;
import com.crammr.studyflashcard.service.NotificationHelper;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private UserRepository userRepository;
    private NotificationRepository notificationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = new UserRepository(getApplication());
        notificationRepository = new NotificationRepository(getApplication());

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnSendResetLink.setOnClickListener(v -> retrieveAndSendPassword());
    }

    private void retrieveAndSendPassword() {
        String email = binding.etResetEmail.getText() != null ? binding.etResetEmail.getText().toString().trim() : "";
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etResetEmail.setError("Vui lòng nhập địa chỉ Gmail hợp lệ");
            binding.etResetEmail.requestFocus();
            return;
        }

        setLoading(true);

        userRepository.retrievePasswordByEmail(email, new UserRepository.PasswordCallback() {
            @Override
            public void onSuccess(String password) {
                runOnUiThread(() -> {
                    setLoading(false);
                    onPasswordRetrieved(email, password);
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void onPasswordRetrieved(String email, String password) {
        // 1. Log notification in app Notification Center
        NotificationItem notif = new NotificationItem(
                "Khôi phục mật khẩu tài khoản",
                "Mật khẩu của tài khoản " + email + " đã được cấp lại thành công.",
                "SECURITY"
        );
        notificationRepository.insertNotification(notif);

        // 2. Show system banner notification
        NotificationHelper.showStudyReminderNotification(
                this,
                "Mật khẩu tài khoản Crammr",
                "Mật khẩu đăng nhập cho " + email + " là: " + password
        );

        // 3. Prepare email dispatch intent
        String subject = "Mật khẩu đăng nhập tài khoản Crammr (Study With FlashCard)";
        String body = "Xin chào,\n\nBạn vừa yêu cầu lấy lại mật khẩu đăng nhập cho tài khoản Crammr.\n\n" +
                "Mật khẩu của bạn là: " + password + "\n\n" +
                "Vui lòng đăng nhập vào ứng dụng và bảo mật thông tin này.\n\n" +
                "Trân trọng,\nĐội ngũ phát triển Crammr.";

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        // 4. Show success dialog displaying the retrieved password and option to open Gmail
        new AlertDialog.Builder(this, R.style.Theme_StudyWithFlashCard)
                .setTitle("Đã lấy lại mật khẩu!")
                .setMessage("Tài khoản đã được xác thực thành công!\n\n" +
                        "📧 Gmail: " + email + "\n" +
                        "🔑 Mật khẩu: " + password + "\n\n" +
                        "Hệ thống đã chuẩn bị thư gửi mật khẩu tới Gmail của bạn. Bạn có muốn mở Gmail để kiểm tra không?")
                .setPositiveButton("Mở Gmail", (dialog, which) -> {
                    try {
                        startActivity(Intent.createChooser(emailIntent, "Gửi email tới " + email));
                    } catch (Exception e) {
                        Toast.makeText(this, "Mật khẩu của bạn: " + password, Toast.LENGTH_LONG).show();
                    }
                    finish();
                })
                .setNegativeButton("Đăng nhập ngay", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void setLoading(boolean isLoading) {
        binding.btnSendResetLink.setEnabled(!isLoading);
        if (isLoading) {
            binding.btnSendResetLink.setText("Đang kiểm tra Firebase…");
            binding.progressRetrievePassword.setVisibility(View.VISIBLE);
        } else {
            binding.btnSendResetLink.setText("Gửi mật khẩu tới Gmail");
            binding.progressRetrievePassword.setVisibility(View.GONE);
        }
    }
}
