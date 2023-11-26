package com.example.saudispot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.saudispot.databinding.ActivitySaudiSpotPostsBinding;

import java.util.ArrayList;
import java.util.List;
//k
public class SaudiSpotPostsActivity extends AppCompatActivity {

    ActivitySaudiSpotPostsBinding binding;
    private List<PostModel> myPostsList;
    public SaudiSpotAdapter saudiSpotAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaudiSpotPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myPostsList = new ArrayList<>();
        myPostsList.clear();


        MyDataBase myDataBase=new MyDataBase(SaudiSpotPostsActivity.this);
        myPostsList=myDataBase.getAllPostsBundle();

        loadRecyclerView(myPostsList);

        binding.btnPostAdd.setOnClickListener(v -> {
            startActivity(new Intent(SaudiSpotPostsActivity.this, PostAddEditActivity.class));
            finish();
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SaudiSpotPostsActivity.this, LoginActivity.class));
            }
        });

    }

    private void loadRecyclerView( List<PostModel> list)
    {
        saudiSpotAdapter = new SaudiSpotAdapter(list, this);
        binding.rvPostList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPostList.setAdapter(saudiSpotAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SaudiSpotPostsActivity.this, LoginActivity.class));
        finish();
    }
}