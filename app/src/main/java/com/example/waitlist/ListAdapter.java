package com.example.waitlist;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Listholder> {
Context mContext;
Cursor mCursor;

    public ListAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    @Override
    public Listholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

View view = LayoutInflater.from(mContext).inflate(R.layout.list_item,viewGroup,false);
        return new Listholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Listholder listholder, int i) {

if (!mCursor.moveToPosition(i)){
    return;
}
Integer id=mCursor.getInt(mCursor.getColumnIndex("id"));
String guestname=mCursor.getString(mCursor.getColumnIndex("guestname"));
Integer partyorder=mCursor.getInt(mCursor.getColumnIndex("partyorder"));


        listholder.order_name.setText(guestname);
        listholder.order.setText(partyorder.toString());
        listholder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public static final class Listholder extends RecyclerView.ViewHolder {
TextView order,order_name;
        public Listholder( View itemView) {
            super(itemView);
            order_name=(TextView)itemView.findViewById(R.id.order_name);
            order=(TextView)itemView.findViewById(R.id.order);
        }
    }
    public void swapcursur(Cursor cursor){
        if (mCursor!=null){
            mCursor.close();

        }
        mCursor=cursor;
        if (cursor!=null)
        {
            this.notifyDataSetChanged();
        }
    }
}

