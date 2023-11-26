package com.example.saudispot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
//l
public class SaudiSpotAdapter extends RecyclerView.Adapter<SaudiSpotAdapter.MyViewHolder> {

    private List<PostModel> list;
    private final Context context;
    private final LayoutInflater inflater;
    private List<LikesModel>likesModels;
    private MyDataBase myDataBase;


    public SaudiSpotAdapter(List<PostModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        likesModels = new ArrayList<>();
        myDataBase=new MyDataBase(this.context);
    }

    @NonNull
    @Override
    public SaudiSpotAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_view_post_bundle, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaudiSpotAdapter.MyViewHolder holder, int position) {
        PostModel myPost = list.get(position);

        holder.image.setImageBitmap(BitmapImageConvertor.getImage(myPost.getPostImage()));
        holder.comment.setText(myPost.getPostComment());
        holder.countLikes.setText(""+myPost.getCountLikes());
        holder.postBy.setText(myPost.getPostBy());
        likesModels = myDataBase.getAllLikes();

        for (LikesModel likesModel : likesModels) {
            if (likesModel.getUserId() == UserHelper.userLogin.getUserId() && likesModel.getPostId() == myPost.getPostId()) {
                holder.imgLikes.setColorFilter(context.getResources().getColor(R.color.red));
                break;
            }
        }

        holder.edit.setOnClickListener(v -> {
            context.startActivity(new Intent(context,PostAddEditActivity.class).putExtra("Post_Id",myPost.getPostId()+""));
        });
        holder.delete.setOnClickListener(v -> {
            deletePostBundle(myPost, holder.getAdapterPosition(), v);
        });
        holder.imgLikes.setOnClickListener(v -> {
            likesModels = myDataBase.getAllLikes();
            boolean userLiked = false;

            for (LikesModel likesModel : likesModels) {
                if (likesModel.getUserId() == UserHelper.userLogin.getUserId() && likesModel.getPostId() == myPost.getPostId()) {
                    userLiked = true;
                    break;
                }
            }

            if (userLiked) {
                holder.imgLikes.setColorFilter(context.getResources().getColor(R.color.black));
                myPost.setCountLikes(myPost.getCountLikes() - 1);
                holder.countLikes.setText("" + myPost.getCountLikes());
                myDataBase.deleteLikes(UserHelper.userLogin.getUserId()+"", myPost.getPostId()+"");
            } else {
                holder.imgLikes.setColorFilter(context.getResources().getColor(R.color.red));
                myPost.setCountLikes(myPost.getCountLikes() + 1);
                holder.countLikes.setText("" + myPost.getCountLikes());

                myDataBase.addLikes(new LikesModel(UserHelper.userLogin.getUserId(), myPost.getPostId()));
            }

            boolean result = myDataBase.updatePostLikesBundle(myPost);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image, imgLikes;
        private final TextView comment,postBy,countLikes;
        private final Button edit, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.ivPostImage);
            edit = itemView.findViewById(R.id.tvPostEdit);
            delete = itemView.findViewById(R.id.tvPostDelete);
            comment = itemView.findViewById(R.id.tvPostText);
            postBy = itemView.findViewById(R.id.tvPostBy);
            imgLikes = itemView.findViewById(R.id.ivLikes);
            countLikes = itemView.findViewById(R.id.tvCountLikes);
        }
    }

    private void deletePostBundle(PostModel postModel, int index, View view) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getRootView().getContext())
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Alert")
                .setMessage("Do you want to delete this Post")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            MyDataBase MyDataBase =new MyDataBase(context.getApplicationContext());
                            boolean is_deleted= MyDataBase.deletePostBundle(postModel.getPostId()+"");

                            if (is_deleted)
                            {
                                Toast.makeText(context.getApplicationContext(), "Post deleted successfully ", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                list.remove(index);
                                notifyItemRemoved(index);
                            }
                            else
                            {
                                Toast.makeText(context.getApplicationContext(), "Error Occurred ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(context.getApplicationContext(), "Exception: "+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alertDialog.show();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
