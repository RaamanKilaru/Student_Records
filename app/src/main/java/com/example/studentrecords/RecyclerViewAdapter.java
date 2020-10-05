package com.example.studentrecords;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.zip.Inflater;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Uri contenturi = Uri.parse(mData.get(position).getImageUri());
        holder.student_image.setImageURI(contenturi);
        holder.student_name.setText(mData.get(position).get_Name());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView student_name;
        ImageView student_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            student_image = (ImageView) itemView.findViewById(R.id.image_card_view);
            student_name = (TextView) itemView.findViewById(R.id.text_card_view);

        }
    }
}
