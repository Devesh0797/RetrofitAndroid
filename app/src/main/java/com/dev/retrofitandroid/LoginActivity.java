package com.dev.retrofitandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.retrofitandroid.Api.RetrofitClient;
import com.dev.retrofitandroid.Models.DefaultResponse;
import com.dev.retrofitandroid.Models.LoginResponse;
import com.dev.retrofitandroid.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText e1,e2;
    private Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1=findViewById(R.id.et_emailid);
        e2=findViewById(R.id.et_passwordid);
        b1=findViewById(R.id.btn_register);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=e1.getText().toString().trim();
                String password=e2.getText().toString().trim();

                Call<LoginResponse> call = RetrofitClient
                        .getInstance().getApi().userLogin(email,password);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginRespon = response.body();
                        if (!loginRespon.isError()) {
                            SharedPrefManager.getInstance(LoginActivity.this)
                                    .saveUser(loginRespon.getUser());
                            Intent intent =new Intent(LoginActivity.this, ProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, loginRespon.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });

            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent =new Intent(LoginActivity.this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
