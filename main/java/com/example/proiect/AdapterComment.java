package com.example.proiect;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<com.example.proiect.AdapterComment.MyHolder> {

    Context context;
    List<ModelComment> list;
    public AdapterComment(Context context, List<ModelComment> list, String myuid, String postid) {
        this.context = context;
        this.list = list;
        this.myuid = myuid;
        this.postid = postid;
    }

    String myuid;
    String postid;


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_comments, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final String uid = list.get(position).getUid();
        String name = list.get(position).getUname();
        String comment = list.get(position).getComment();
        String timestamp = list.get(position).getPtime();

        holder.name.setText(name);
        holder.time.setText(timestamp);
        holder.comment.setText(comment);
        try {
            DatabaseHelper mydb=new DatabaseHelper(holder.itemView.getContext());
            Cursor cursor2=mydb.get_profile_image_post(uid);
            cursor2.moveToNext();
            Bitmap bmp = BitmapFactory.decodeByteArray(cursor2.getBlob(0), 0, cursor2.getBlob(0).length);
            holder.imagea.setImageBitmap(bmp);
        } catch (Exception e) {

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView imagea;
        TextView name, comment, time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imagea = itemView.findViewById(R.id.loadcomment);
            name = itemView.findViewById(R.id.commentname);
            comment = itemView.findViewById(R.id.commenttext);
            time = itemView.findViewById(R.id.commenttime);
        }
    }
}
