package com.example.studentrecords;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<StudentInfo> mData;

    public RecyclerViewAdapter(Context mContext, List<StudentInfo> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.student_image.setImageURI(Uri.parse(mData.get(position).getImageUri()));
        holder.student_name.setText(mData.get(position).get_Name());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SAI","item clicked");
                Intent preview = new Intent(v.getContext(),StudentInfoView.class);

                //Shared View Transition Animation
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(),v.findViewById(R.id.image_card_view),"myImage");

                preview.putExtra("image_uri", mData.get(position).getImageUri());
                preview.putExtra("name", mData.get(position).get_Name());
                preview.putExtra("roll_no", mData.get(position).getRoll_no());
                preview.putExtra("gender", mData.get(position).getGender());
                preview.putExtra("qualification", mData.get(position).getQualification());
                preview.putExtra("d_o_b", mData.get(position).getDob());
                Log.i("recycler_tag", String.valueOf(preview.hasExtra("d_o_b")));
                v.getContext().startActivity(preview,optionsCompat.toBundle());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(),"DOB : " + mData.get(position).getDob() + "; Age : " + mData.get(position).getAge(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView student_name;
        ImageView student_image;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            student_image = (ImageView) itemView.findViewById(R.id.image_card_view);
            student_name = (TextView) itemView.findViewById(R.id.text_card_view);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }
    }
}
