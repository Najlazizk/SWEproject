package com.example.saudispot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.saudispot.databinding.ActivityAddEditPostBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
//k
public class PostAddEditActivity extends AppCompatActivity {
    ActivityAddEditPostBinding binding;
    private Uri uri=null;
    private PostModel postModel;
    private int myPostId;
    private boolean stateSaveOrUpdate =false;
    private boolean stateImageChanged =false;
    public final int REQUEST_CODE_GALLERY =45;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            myPostId = Integer.parseInt(extras.getString("Post_Id"));
            if(publishPost())
            {
                stateSaveOrUpdate = true;
                fillViews();
            }
        }

        binding.ivGallery.setOnClickListener(v -> {

            Intent img_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            img_intent.setType("image/*");
            startActivityForResult(img_intent, 45);
        });

        binding.btnPostSubmit.setOnClickListener(v -> {

            savePost();
        });

        binding.ivBack.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SaudiSpotPostsActivity.class));
            finish();
        });
    }
    public boolean publishPost() {

        try {
            MyDataBase MyDataBase =new MyDataBase(this);
            Cursor cursor= MyDataBase.getPostBundle(myPostId);

            if (cursor != null && cursor.moveToFirst())
            {
                postModel =new PostModel();
                // Passing values
                postModel.setPostId(Integer.parseInt(cursor.getString(0)));
                postModel.setPostComment(cursor.getString(1));
                postModel.setPostImage(cursor.getBlob(2));
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            Toast.makeText(PostAddEditActivity.this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== REQUEST_CODE_GALLERY && resultCode==RESULT_OK && data !=null) {
            uri = data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
                bitmap= BitmapFactory.decodeStream(inputStream);
                // Resize Image
                bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                binding.ivPostImage.setImageBitmap(bitmap);
                stateImageChanged =true;

            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void savePost()
    {

        if (binding.ivPostImage.getDrawable() == null
                && uri==null)
        {
            Toast.makeText(this, "Please choose Image", Toast.LENGTH_SHORT).show();
        }
        else if (binding.etPostText.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter post description", Toast.LENGTH_SHORT).show();
        }
        else {
            String post_comment = binding.etPostText.getText().toString().trim();


            if (stateSaveOrUpdate)
            {
                postModel.setPostComment(post_comment);
            }

            if (uri != null)
            {
                if (stateImageChanged && stateSaveOrUpdate)
                {

                    postModel.setPostImage(BitmapImageConvertor.getBytes(bitmap));
                    updateMyPostObj();
                }
                else
                {

//
                    int userId=UserHelper.userLogin.getUserId();
                    String postBy=UserHelper.userLogin.getUserName();
                    PostModel postModel = new PostModel(post_comment, BitmapImageConvertor.getBytes(bitmap),0,userId,postBy);
                    addPostObj(postModel);
                }
            }
            else
            {

                updateMyPostObj();
            }
        }
    }
    public void addPostObj(PostModel postModel)
    {
        try {
            MyDataBase MyDataBase =new MyDataBase(this);
            boolean is_inserted= MyDataBase.addPostBundle(postModel);

            if (is_inserted)
            {
                clearView();
                Toast.makeText(getApplicationContext(), "Post saved successfully ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SaudiSpotPostsActivity.class));
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Error Occurred ", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Exception: "+e, Toast.LENGTH_SHORT).show();
        }

    }
    public void updateMyPostObj()
    {
        MyDataBase MyDataBase =new MyDataBase(this);
        boolean is_updated= MyDataBase.updatePostBundle(postModel);

        if (is_updated)
        {
            clearView();
            Toast.makeText(getApplicationContext(), "Post updated successfully ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), SaudiSpotPostsActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error Occurred ", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PostAddEditActivity.this, SaudiSpotPostsActivity.class));
        finish();
    }
    public void fillViews()
    {
        binding.ivPostImage.setImageBitmap(BitmapImageConvertor.getImage(postModel.getPostImage()));
        binding.etPostText.setText(postModel.getPostComment());
    }
    public void clearView()
    {
        binding.etPostText.setText("");
        uri=null;
        stateImageChanged=false;
        stateSaveOrUpdate=false;
    }
}