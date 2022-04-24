package com.ynk.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ynk.todolist.Activitys.LoginActivity;
import com.ynk.todolist.Database.AppDatabase;
import com.ynk.todolist.Database.DAO;
import com.ynk.todolist.Model.User;
import com.ynk.todolist.Tools.SendMailTask;
import com.ynk.todolist.Tools.Utils;

import muyan.snacktoa.SnackToa;


import java.util.Arrays;
import java.util.List;

public class ForgetPassword extends AppCompatActivity {
  private DAO dao;
    String code;
    User user;


    @Override
  protected void onCreate(Bundle savedInstanceState) {
    final TextView backToSignIn, sendCode, tvForgetPassCode, etForgetPassCode, tvNewPassword, etNewPassword;
    final Button btnResetPassword, btnVerifyForgetPassCode;
    final EditText etForgetPassEmail;

    dao = AppDatabase.getDb(this).getDAO();


    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forget_password);

    backToSignIn = findViewById(R.id.backToSignIn);
    sendCode = findViewById(R.id.sendCode);
    etForgetPassEmail = findViewById(R.id.etForgetPassEmail);
    tvForgetPassCode = findViewById(R.id.tvForgetPassCode);
    etForgetPassCode = findViewById(R.id.etForgetPassCode);
    tvNewPassword = findViewById(R.id.tvNewPassword);
    etNewPassword = findViewById(R.id.etNewPassword);
    btnResetPassword = findViewById(R.id.btnResetPassword);
    btnVerifyForgetPassCode = findViewById(R.id.btnVerifyForgetPassCode);

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
        boolean isError = false;
        if (TextUtils.isEmpty(etForgetPassEmail.getText().toString().trim())) {
          etForgetPassEmail.setError(getString(R.string.forgetPassEmailError));
          isError = true;
        }

        if (isError) return;

        user = dao.forgetPasswordControl(etForgetPassEmail.getText().toString());
        if (user != null) {
          code = Utils.getRandomString(6);

          sendForgetPasswordCode(etForgetPassEmail.getText().toString(), code);

            tvForgetPassCode.setVisibility(View.VISIBLE);
            etForgetPassCode.setVisibility(View.VISIBLE);

            btnVerifyForgetPassCode.setVisibility(View.VISIBLE);
            tvForgetPassCode.setVisibility(View.VISIBLE);
            etForgetPassCode.setVisibility(View.VISIBLE);
        } else {
          SnackToa.snackBarError(ForgetPassword.this, getString(R.string.wrongEmailErrorMessage));
        }
      }
    });

      btnVerifyForgetPassCode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String verifyingCode = etForgetPassCode.getText().toString();
              if (verifyingCode.equals(code)) {
                  btnVerifyForgetPassCode.setVisibility(View.GONE);
                  tvForgetPassCode.setVisibility(View.GONE);
                  etForgetPassCode.setVisibility(View.GONE);

                  tvNewPassword.setVisibility(View.VISIBLE);
                  etNewPassword.setVisibility(View.VISIBLE);
                  btnResetPassword.setVisibility(View.VISIBLE);
              } else {
                  SnackToa.snackBarError(ForgetPassword.this, "Code doesn't match");
              }
          }
      });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNewPassword.getText().toString().trim())) {
                    etNewPassword.setError(getString(R.string.signUpPasswordError));
                } else {
                    user.setUserPassword(etNewPassword.getText().toString());
                    dao.insertUser(user);
                    SnackToa.snackBarSuccess(ForgetPassword.this, "Password updated successfully!");
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
  }

  void sendForgetPasswordCode(String emailTo, String code) {
      Log.i("SendMailActivity", "Send Button Clicked.");

      String fromEmail = "urlineups@gmail.com";
      String fromPassword = "lineups1";
      List<String> toEmailList = Arrays.asList(emailTo
          .split("\\s*,\\s*"));
      Log.i("SendMailActivity", "To List: " + toEmailList);
      String emailBody = "Forget Password Code: " + code;
      String emailSubject = "Reset Password Code";
      new SendMailTask(ForgetPassword.this).execute(fromEmail,
          fromPassword, toEmailList, emailSubject, emailBody);
  }
}