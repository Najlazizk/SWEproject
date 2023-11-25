package com.example.saudispot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saudispot.databinding.ActivityLoginBinding;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity{

    ActivityLoginBinding binding;
    private List<UsersModel> usersModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usersModelList =new ArrayList<>();

        binding.btnLogin.setOnClickListener(v -> {
            login();
        });
        binding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });
    }
    private void login()
    {
        if (binding.etUserEmail.getText().toString().isEmpty()
                ||binding.etUserPassword.getText().toString().isEmpty())
        {
            Toast.makeText(LoginActivity.this,"Please enter both Email and Password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String email=binding.etUserEmail.getText().toString().trim();
            String password=binding.etUserPassword.getText().toString().trim();

            MyDataBase myDataBase=new MyDataBase(LoginActivity.this);
            UsersModel userObj=myDataBase.getUser(email,password);

            if (userObj!=null)
            {
                if (userObj.getUserEmail().equals(email) && userObj.getUserPassword().equals(password))
                {
                    Toast.makeText(LoginActivity.this,"Login successfully",Toast.LENGTH_SHORT).show();
                    UserHelper.userLogin=userObj;
                    startActivity(new Intent(LoginActivity.this,SaudiSpotPostsActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "UserName or Password not matched", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(LoginActivity.this, "UserName or Password not matched", Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}