package com.example.saudispot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saudispot.databinding.ActivityLoginBinding;
import com.example.saudispot.databinding.ActivitySignUpBinding;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;

    private EditText et_Email;
    private EditText et_Password;
    private EditText et_userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();


        binding.btnSignUp.setOnClickListener(v -> {
            signUp();
        });
        binding.tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

    }
    private void signUp()
    {
        if(et_userName.getText().toString().isEmpty()
                || et_Email.getText().toString().isEmpty()
                || et_Password.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else
        {

                UsersModel usersModel = new UsersModel(
                        binding.etUserName.getText().toString().trim(),
                        binding.etUserEmail.getText().toString().trim(),
                        binding.etUserPassword.getText().toString().trim()
                );
                MyDataBase myDataBase=new MyDataBase(SignUpActivity.this);
                boolean result=myDataBase.addUsers(usersModel);

                if (result==true)
                {
                    clearView();
                    Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(this, "Some thing wrong, User not registered", Toast.LENGTH_SHORT).show();
                }


        }
    }
    private void init()
    {
        et_userName=findViewById(R.id.et_UserName);
        et_Email=findViewById(R.id.et_UserEmail);
        et_Password=findViewById(R.id.et_UserPassword);
    }
    private void clearView() {
        et_userName.setText("");
        et_Email.setText("");
        et_Password.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }
}