package com.dev.retrofitandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.retrofitandroid.Api.RetrofitClient;
import com.dev.retrofitandroid.Models.DefaultResponse;
import com.dev.retrofitandroid.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText t1,t2,t3,t4;
    private Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1=findViewById(R.id.et_emailid);
        t2=findViewById(R.id.et_passwordid);
        t3=findViewById(R.id.et_nameid);
        t4=findViewById(R.id.et_schoolid);

        b1=findViewById(R.id.btn_login);
        b2=findViewById(R.id.btn_register);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=t1.getText().toString().trim();
                String password=t2.getText().toString().trim();
                String name=t3.getText().toString().trim();
                String school=t4.getText().toString().trim();

                Call<DefaultResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .createUser(email,password,name,school);

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            if (response.code() == 201) {
                                 DefaultResponse dr = response.body();
                                Toast.makeText(MainActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                            }
                            else if( response.code() == 422) {

                                Toast.makeText(MainActivity.this, "User already exist", Toast.LENGTH_LONG).show();
                            }
                        }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent =new Intent(MainActivity.this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
