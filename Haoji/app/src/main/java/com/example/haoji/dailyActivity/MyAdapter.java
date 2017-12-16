package com.example.haoji.dailyActivity;



import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haoji.R;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private ArrayList<String> mData;
    public OnItemClickListner mOnItemClickListener=null;
    public MyAdapter(ArrayList<String> data) {
        this.mData = data;


    }

    public void updateData(ArrayList<String>data){
        this.mData = data;

        notifyDataSetChanged();
    }
    public static interface OnItemClickListner{
        void onItemClick(View view,int position);

    }


/*mData.clear();
        mData.add(data);
        MyAdapter.notifyDataSetChange();*/


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.mTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v,(int)v.getTag());
                }
            }

        });
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // 绑定数据
        holder.mTv.setText(mData.get(position));
        holder.mTv.setTag(position);
        if(mOnItemClickListener!=null){
            holder.mTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);

                }
            });
        }

        /*int position = cursor.getPosition();

        holder.tv_dateTime.setText(cursor.getString(cursor.getColumnIndex(NoteDbAdapter.COL_DATETIME)));
        holder.mRowtab.setBackgroundColor(cursor.getInt(cursor.getColumnIndex(NoteDbAdapter.COL_IMPORTANT)) == 1?
                mContext.getResources().getColor(R.color.colorAccent):mContext.getResources().getColor(android.R.color.white)
        );
        holder.root.setTag(position);

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(view, holder.getAdapterPosition());
                }
            }
        });


    }*/

    }



    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}