package com.example.igo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class Frag_list extends ListFragment {
    private View view;
    private ListViewAdapter adapter;

   // public String name() {
  //      return getArguments().getString("name");
   // }

   // public String phone() {
   //     return getArguments().getString("phone");
  //  }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_list, container, false);

        adapter = new ListViewAdapter();
        setListAdapter(adapter);
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_account_circle_24), "조홍래","333-3333","긴급!");
     //   adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_account_circle_24), name(),phone(),"정상");

        return super.onCreateView(inflater,container,savedInstanceState);
        //return view;
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        // get TextView's Text.
   //     adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_account_circle_24), "조홍래","333-3333","긴급!");
        ListViewItem item = (ListViewItem) l.getItemAtPosition(position) ;
        String nameStr = item.getName() ;
        String phoneStr = item.getPhone() ;
        String stateStr = item.getState();
        Drawable iconDrawable = item.getIcon() ;
        // TODO : use item data.
    }

    public void addItem(Drawable icon, String name, String phone, String state) {
        adapter.addItem(icon, name, phone, state);
    }
}
