package com.example.saudispot;

import java.io.Serializable;

public class LikesModel implements Serializable {
    private int likesId;
    private int userId;
    private int postId;

    public LikesModel() {
    }
    public LikesModel(int userId, int postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public int getLikesId() {
        return likesId;
    }

    public void setLikesId(int likesId) {
        this.likesId = likesId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
