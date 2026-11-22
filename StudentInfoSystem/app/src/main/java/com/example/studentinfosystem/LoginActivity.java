package com.example.studentinfosystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    // Demo credentials (a real app would check a database or server)
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "1234";

    private TextInputLayout layoutUsername, layoutPassword;
    private TextInputEditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutUsername = findViewById(R.id.layoutUsername);
        layoutPassword = findViewById(R.id.layoutPassword);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        layoutUsername.setError(null);
        layoutPassword.setError(null);

        String username = text(etUsername);
        String password = text(etPassword);

        boolean valid = true;
        if (username.isEmpty()) {
            layoutUsername.setError(getString(R.string.err_required));
            valid = false;
        }
        if (password.isEmpty()) {
            layoutPassword.setError(getString(R.string.err_required));
            valid = false;
        }
        if (!valid) return;

        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            Intent intent = new Intent(this, StudentInfoActivity.class);
            intent.putExtra(StudentInfoActivity.EXTRA_USERNAME, username);
            startActivity(intent);
            finish();
        } else {
            layoutPassword.setError(getString(R.string.err_login));
        }
    }

    private String text(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}
