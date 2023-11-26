package com.example.saudispot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
//u
public class MyDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "SaudiSpotDB";
    private static final String USER_TABLE_NAME = "UsersTable";
    private static final String POST_TABLE_NAME = "SaudiSpotTable";
    private static final String LIKES_TABLE_NAME = "LikesTable";
    private static final int DB_VERSION = 1;


    public MyDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryCreateUserTable= "CREATE TABLE " + USER_TABLE_NAME +  "(UserId INTEGER PRIMARY KEY,UserName TEXT,UserEmail TEXT,UserPassword TEXT)";
        db.execSQL(queryCreateUserTable);

        String queryCreatePostTable = "CREATE TABLE " + POST_TABLE_NAME + "(PostId INTEGER PRIMARY KEY AUTOINCREMENT, Comment VARCHAR, Image BLOB, CountLikes INTEGER, UserId INTEGER, PostBy VARCHAR)";
        db.execSQL(queryCreatePostTable);

        String queryLikesTable = "CREATE TABLE " + LIKES_TABLE_NAME + "(LikesId INTEGER PRIMARY KEY AUTOINCREMENT, UserId INTEGER, PostId INTEGER)";
        db.execSQL(queryLikesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LIKES_TABLE_NAME);
    }

    public boolean addLikes(LikesModel likesModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("UserId", likesModel.getUserId());
        contentValues.put("PostId", likesModel.getPostId());

        long result = db.insert(LIKES_TABLE_NAME, null, contentValues);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
    public List<LikesModel> getAllLikes() {

        List<LikesModel> likesList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String RETRIEVE_RECORD_QUERY = "SELECT * FROM " + LIKES_TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(RETRIEVE_RECORD_QUERY, null);

        if (cursor != null && cursor.moveToFirst()){
            do {
                LikesModel likesModel =new LikesModel();
                // Passing values
                likesModel.setLikesId(Integer.parseInt(cursor.getString(0)));
                likesModel.setUserId(Integer.parseInt(cursor.getString(1)));
                likesModel.setPostId(Integer.parseInt(cursor.getString(2)));

                likesList.add(likesModel);

            } while(cursor.moveToNext());
        }
        return likesList;
    }
    public boolean deleteLikes(String userId,String postId) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        String whereClause = "UserId=? AND PostId=?";
//        String[] whereArgs = new String[]{String.valueOf(userId), String.valueOf(postId)};
//        int result = sqLiteDatabase.delete("LikesTable", whereClause, whereArgs);

        int result = sqLiteDatabase.delete(LIKES_TABLE_NAME, "UserId=? AND PostId=?", new String[]{userId,postId});

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean addUsers(UsersModel usersModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("UserName", usersModel.getUserName());
        contentValues.put("UserEmail", usersModel.getUserEmail());
        contentValues.put("UserPassword", usersModel.getUserPassword());

        long result = db.insert(USER_TABLE_NAME, null, contentValues);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
    public UsersModel getUser(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String RETRIEVE_RECORD_QUERY = "SELECT * FROM " + USER_TABLE_NAME + " WHERE UserEmail = '"+ email +"' AND UserPassword = '"+password+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(RETRIEVE_RECORD_QUERY, null);
        UsersModel userObject=new UsersModel();
        if (cursor!=null && cursor.moveToFirst())
        {
            userObject.setUserId(cursor.getInt(0));
            userObject.setUserName(cursor.getString(1));
            userObject.setUserEmail(cursor.getString(2));
            userObject.setUserPassword(cursor.getString(3));
            return  userObject;
        }
        else
        {
            return  null;
        }
    }
    public Cursor getPostBundle(int postId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String RETRIEVE_RECORD_QUERY = "SELECT * FROM " + POST_TABLE_NAME + " WHERE PostId = '"+ postId +"'";
        Cursor cursor = sqLiteDatabase.rawQuery(RETRIEVE_RECORD_QUERY, null);

        return cursor;
    }
    public List<PostModel> getAllPostsBundle() {

        List<PostModel> postList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String RETRIEVE_RECORD_QUERY = "SELECT * FROM " + POST_TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(RETRIEVE_RECORD_QUERY, null);

        if (cursor != null && cursor.moveToFirst()){
            do {
                PostModel postModel =new PostModel();
                // Passing values
                postModel.setPostId(Integer.parseInt(cursor.getString(0)));
                postModel.setPostComment(cursor.getString(1));
                postModel.setPostImage(cursor.getBlob(2));
                postModel.setCountLikes(Integer.parseInt(cursor.getString(3)));
                postModel.setUserId(Integer.parseInt(cursor.getString(4)));
                postModel.setPostBy(cursor.getString(5));

                postList.add(postModel);

            } while(cursor.moveToNext());
        }
        return postList;
    }
    public boolean addPostBundle(PostModel postModel) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Comment", postModel.getPostComment());
        values.put("Image", postModel.getPostImage());
        values.put("CountLikes", postModel.getCountLikes());
        values.put("UserId", postModel.getUserId());
        values.put("PostBy", postModel.getPostBy());


        Long l = sqLiteDatabase.insert(POST_TABLE_NAME, "", values);


        if (l > 0) {
            return true;
        } else {
            return false;
        }

    }
    public boolean deletePostBundle(String item_ID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        int result = sqLiteDatabase.delete(POST_TABLE_NAME, "PostId=?", new String[]{item_ID});

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean updatePostBundle(PostModel postModel) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Comment", postModel.getPostComment());
        values.put("Image", postModel.getPostImage());

        int result = sqLiteDatabase.update(POST_TABLE_NAME, values, "PostId=?", new String[]{postModel.getPostId() + ""});

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean updatePostLikesBundle(PostModel postModel) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("CountLikes", postModel.getCountLikes());

        int result = sqLiteDatabase.update(POST_TABLE_NAME, values, "PostId=?", new String[]{postModel.getPostId() + ""});

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

}
