package com.example.haoji.dailyActivity;

/**
 * Created by Administrator on 2017/11/19.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haoji.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private ArrayList<String> mData;
    public MyAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

/*private SQLiteOpenHelper moh;
    private SQLiteDatabase sd;
    private ArrayList<String> mData;
    moh=new Database(this,"Database.db",null,2);
    RecyclerView.setAdapter(new RecycleViewAdapter(
            moh.getData(),R.layout.custom_row));
    SQLiteDatabase sqLiteDatabase= moh.getWritableDatabase();
    Cursor cursor= sqLiteDatabase.rawQuery("select *from Schedule",null);
    while (cursor.moveToNext()){

        int index0=cursor.getColumnIndex(moh.UID);

        int index1=cursor.getColumnIndex(helper.KEY_NAME);
        int index2=cursor.getColumnIndex(helper.KEY_VALUE);
        int index3=cursor.getColumnIndex(helper.KEY_FORMAT);
        int index4=cursor.getColumnIndex(helper.KEY_COUNTRY);

        int cid = cursor.getInt(index0);
        String name = cursor.getString(index1);
        String value = cursor.getString(index2);
        String format = cursor.getString(index3);
        String country = cursor.getString(index4);
        String cards = new String(cid,name,value,format,country);

        mData.add(cards);

    }

    return mData;

}
*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        holder.mTv.setText(mData.get(position));

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