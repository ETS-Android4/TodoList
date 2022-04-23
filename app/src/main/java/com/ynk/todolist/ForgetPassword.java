package com.ynk.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ynk.todolist.Activitys.LoginActivity;

public class ForgetPassword extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    final TextView backToSignIn, sendCode, tvForgetPassCode, etForgetPassCode, tvNewPassword, etNewPassword;
    final Button btnResetPassword;

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forget_password);

    backToSignIn = findViewById(R.id.backToSignIn);
    sendCode = findViewById(R.id.sendCode);
    tvForgetPassCode = findViewById(R.id.tvForgetPassCode);
    etForgetPassCode = findViewById(R.id.etForgetPassCode);
    tvNewPassword = findViewById(R.id.tvNewPassword);
    etNewPassword = findViewById(R.id.etNewPassword);
    btnResetPassword = findViewById(R.id.btnResetPassword);

    backToSignIn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
      }
    });

    sendCode.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        tvForgetPassCode.setVisibility(View.VISIBLE);
        etForgetPassCode.setVisibility(View.VISIBLE);
        tvNewPassword.setVisibility(View.VISIBLE);
        etNewPassword.setVisibility(View.VISIBLE);
        btnResetPassword.setVisibility(View.VISIBLE);
      }
    });

  }
}