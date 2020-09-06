package com.example.igo.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.igo.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<ListItem> mData = null;

    RecyclerAdapter(ArrayList<ListItem> list) {
        mData = list;
    }
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycler_item, parent, false) ;
        RecyclerAdapter.ViewHolder vh = new RecyclerAdapter.ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        ListItem item = mData.get(position) ;

        holder.name.setText(item.name);
        holder.phone.setText(item.phone);
        holder.state.setText(item.state);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView state;
        TextView phone;

        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textName);
            state = itemView.findViewById(R.id.textState);
            phone = itemView.findViewById(R.id.textPhone);
        }
    }

}

