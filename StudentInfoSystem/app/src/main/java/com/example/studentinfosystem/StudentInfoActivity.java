package com.example.studentinfosystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class StudentInfoActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "extra_username";

    private TextInputLayout layoutStudentNo, layoutLastName, layoutFirstName,
            layoutProgram, layoutYearLevel;
    private TextInputEditText etStudentNo, etLastName, etFirstName, etMiddleName,
            etSpecialization;
    private AutoCompleteTextView actProgram, actYearLevel;
    private View cardResult;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        bindViews();

        String username = getIntent().getStringExtra(EXTRA_USERNAME);
        ((TextView) findViewById(R.id.tvWelcome)).setText("Logged in as: " + username);

        actProgram.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.programs)));
        actYearLevel.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.year_levels)));

        findViewById(R.id.btnSave).setOnClickListener(v -> saveRecord());
        findViewById(R.id.btnClear).setOnClickListener(v -> clearForm());
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void bindViews() {
        layoutStudentNo = findViewById(R.id.layoutStudentNo);
        layoutLastName = findViewById(R.id.layoutLastName);
        layoutFirstName = findViewById(R.id.layoutFirstName);
        layoutProgram = findViewById(R.id.layoutProgram);
        layoutYearLevel = findViewById(R.id.layoutYearLevel);

        etStudentNo = findViewById(R.id.etStudentNo);
        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.etFirstName);
        etMiddleName = findViewById(R.id.etMiddleName);
        etSpecialization = findViewById(R.id.etSpecialization);
        actProgram = findViewById(R.id.actProgram);
        actYearLevel = findViewById(R.id.actYearLevel);

        cardResult = findViewById(R.id.cardResult);
        tvResult = findViewById(R.id.tvResult);
    }

    private void saveRecord() {
        boolean valid = true;
        valid &= require(layoutStudentNo, text(etStudentNo));
        valid &= require(layoutLastName, text(etLastName));
        valid &= require(layoutFirstName, text(etFirstName));
        valid &= require(layoutProgram, actProgram.getText().toString().trim());
        valid &= require(layoutYearLevel, actYearLevel.getText().toString().trim());
        if (!valid) return;

        String middle = text(etMiddleName);
        String specialization = text(etSpecialization);

        StringBuilder sb = new StringBuilder();
        sb.append("Student No.: ").append(text(etStudentNo)).append("\n");
        sb.append("Last name: ").append(text(etLastName)).append("\n");
        sb.append("First name: ").append(text(etFirstName)).append("\n");
        sb.append("Middle name: ").append(middle.isEmpty() ? "—" : middle).append("\n");
        sb.append("Program: ").append(actProgram.getText()).append("\n");
        sb.append("Specialization: ")
                .append(specialization.isEmpty() ? "None" : specialization).append("\n");
        sb.append("Year level: ").append(actYearLevel.getText());

        tvResult.setText(sb.toString());
        cardResult.setVisibility(View.VISIBLE);
    }

    private boolean require(TextInputLayout layout, String value) {
        if (value.isEmpty()) {
            layout.setError(getString(R.string.err_required));
            return false;
        }
        layout.setError(null);
        return true;
    }

    private void clearForm() {
        etStudentNo.setText("");
        etLastName.setText("");
        etFirstName.setText("");
        etMiddleName.setText("");
        etSpecialization.setText("");
        actProgram.setText("", false);
        actYearLevel.setText("", false);
        layoutStudentNo.setError(null);
        layoutLastName.setError(null);
        layoutFirstName.setError(null);
        layoutProgram.setError(null);
        layoutYearLevel.setError(null);
        cardResult.setVisibility(View.GONE);
    }

    private String text(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}
