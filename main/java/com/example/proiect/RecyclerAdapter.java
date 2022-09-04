package com.example.proiect;

import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Feed> arrayList;

    public RecyclerAdapter(ArrayList<Feed> arrayList){

        this.arrayList=arrayList;

    }

    public void setFilteredList(ArrayList<Feed> filteredList){
        this.arrayList=filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feed feed = arrayList.get(position);

        holder.title.setText(feed.getTitle());
        holder.message.setText(feed.getMessage());
        holder.profileImage.setImageResource(feed.getProfileIcon());
        holder.postImage.setImageBitmap(feed.getPostImage());


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        ImageView postImage;
        TextView title;
        TextView message;
        ImageButton like_button;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.ivProfile);
            postImage = itemView.findViewById(R.id.ivPost);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            like_button=itemView.findViewById(R.id.ivLike);

            DatabaseHelper mydb=new DatabaseHelper(itemView.getContext());

            TextView like_counter=itemView.findViewById(R.id.like_counter);
            like_counter.setText(String.valueOf(mydb.get_like_counter(getAdapterPosition()+1)));

            itemView.findViewById(R.id.ivLike).setOnClickListener(view ->{
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                if(currentFirebaseUser==null)
                    Toast.makeText(itemView.getContext(), "Trebuie sa fiti autentificat pentru a aprecia postarea",
                            Toast.LENGTH_SHORT).show();
                else{
                    int aux=getAdapterPosition()+1;
                    if(mydb.check_if_like_unique(aux,MainActivity.id_user_curent)==1) {
                        mydb.add_like_to_post(aux,MainActivity.id_user_curent);
                        Toast.makeText(itemView.getContext(), "Ati apreciat postarea", Toast.LENGTH_SHORT).show();
                        like_counter.setText(String.valueOf(mydb.get_like_counter(getAdapterPosition()+1)));

                    }
                    else
                    {
                        mydb.undo_like_to_post(aux,MainActivity.id_user_curent);
                        Toast.makeText(itemView.getContext(), "Acum nu mai apreciati postarea", Toast.LENGTH_SHORT).show();
                        like_counter.setText(String.valueOf(mydb.get_like_counter(getAdapterPosition()+1)));
                    }
                    }
            });
            itemView.findViewById(R.id.ivComment).setOnClickListener(view ->{
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                if(currentFirebaseUser==null)
                    Toast.makeText(itemView.getContext(), "Trebuie sa fiti autentificat pentru a putea comenta",
                            Toast.LENGTH_LONG).show();
                else
                {
//                    Intent sectiune_comentarii = new Intent(itemView.getContext(),CommentActivity.class);
//                    itemView.getContext().startActivity(sectiune_comentarii);

                }

            });
//        if(MyPosts.active) {
//            ImageView btn=(ImageView)itemView.findViewById(R.id.menu);
//            btn.setImageResource(R.drawable.ic_baseline_delete_24);
//            itemView.findViewById(R.id.menu).setOnClickListener(view -> {
//                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                int aux=getAdapterPosition()+1;
//                mydb.delete_post(aux,MainActivity.id_user_curent);
//                notifyDataSetChanged();
//            });
//        }

            itemView.findViewById(R.id.ivPost).setOnClickListener(view->{
                like_counter.setText(String.valueOf(mydb.get_like_counter(getAdapterPosition()+1)));
                itemView.findViewById(R.id.ivPost).setSoundEffectsEnabled(false);
            });


            itemView.findViewById(R.id.ivBookmark).setOnClickListener(view ->{
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                if(currentFirebaseUser==null)
                    Toast.makeText(itemView.getContext(), "Trebuie sa fiti autentificat pentru a pune un semn de carte",
                            Toast.LENGTH_SHORT).show();
                else{
                    int aux=getAdapterPosition()+1;
                    if(mydb.check_if_bookmark_unique(aux,MainActivity.id_user_curent)==1)
                    {
                        mydb.addBookmark(MainActivity.id_user_curent,aux);
                    }
                    else
                    {
                        Toast.makeText(itemView.getContext(), "Deja ati pus un semn de carte pentru aceasta postare",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.findViewById(R.id.ivShare).setOnClickListener(view->{
                int aux=getPosition()+1;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Titlu_reteta";
                String Sub = "Detalii";
                Cursor cursor=mydb.get_title_and_content_from_post(aux);
                cursor.moveToNext();
                Body= cursor.getString(0);
                Sub=cursor.getString(1);
                intent.putExtra(Intent.EXTRA_TEXT, Body+"\n"+Sub);
                itemView.getContext().startActivity(Intent.createChooser(intent, "Trimite cu ajutorul:"));
            });
        }
    }
}
