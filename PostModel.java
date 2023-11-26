package com.example.saudispot;

import java.io.Serializable;
//l
public class PostModel implements Serializable {

    private int postId;
    private String postComment;
    private byte[] postImage;
    private int countLikes;
    private int UserId;
    private String postBy;

    public PostModel() {
    }

    public PostModel(String postComment, byte[] postImage, int countLikes, int userId,String postBy) {
        this.postComment = postComment;
        this.postImage = postImage;
        this.countLikes=countLikes;
        this.UserId=userId;
        this.postBy =postBy;
    }
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public byte[] getPostImage() {
        return postImage;
    }

    public void setPostImage(byte[] postImage) {
        this.postImage = postImage;
    }

    public String getPostComment() {
        return postComment;
    }

    public void setPostComment(String postComment) {
        this.postComment = postComment;
    }

    public int getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(int countLikes) {
        this.countLikes = countLikes;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getPostBy() {
        return postBy;
    }

    public void setPostBy(String postBy) {
        this.postBy = postBy;
    }
}
