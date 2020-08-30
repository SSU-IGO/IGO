package com.example.igo.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.igo.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private Context context;
    private List<ListViewItem> patientList;
    //private List<ListViewItem> patientList;

    // ListViewAdapter의 생성자
    public ListViewAdapter(Context context, List<ListViewItem> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        //리스트의 개수를 알려주는 역할
        return patientList.size() ;
    }
    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //각각의 아이템을 위한 뷰를 지정
        //이 메소드 안에서 리스트뷰안에 데이터들이 어떤 view의 형태로 보여질ㅈ 정해짐
        final int pos = position;
        final Context context = parent.getContext();
        View v = View.inflate(context, R.layout.list_fragment,null);

        // "listview_item" Layout을 inflate하여 view 참조 획득.
    //    if (view == null) {
    //       LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //        view = inflater.inflate(R.layout.listview_item, parent, false);
    //    }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) view.findViewById(R.id.imageView1) ;
        TextView name = (TextView) view.findViewById(R.id.textView1) ;
        TextView phone_n = (TextView) view.findViewById(R.id.textView2) ;
        TextView state = (TextView) view.findViewById(R.id.textView3) ;
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        //ListViewItem listViewItem = patientList.get(position);

        iconImageView.setImageDrawable(patientList.get(position).getIcon());
        name.setText(patientList.get(position).getName());
        phone_n.setText(patientList.get(position).getPhone());
        state.setText(patientList.get(position).getState());
        // 아이템 내 각 위젯에 데이터 반영
    /*    iconImageView.setImageDrawable(listViewItem.getIcon());
        name.setText(listViewItem.getName());
        phone_n.setText(listViewItem.getPhone());
        state.setText(listViewItem.getState());*/

        return view;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        //아이템의 아이디 값은 인덱스로 받아옴. 앞에서 각각의 아이템에 포지션을 통해 인덱스가 정해졌으니 이 포지션을 받아오면됨
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        //각각의 아이템의 인덱스를 통해 아이템을 하나씩 받아오는 역할
        return patientList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
  /*  public void addItem(Drawable icon, String name, String phone_n, String state) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setName(name);
        item.setPhone(phone_n);
        item.setState(state);

        patientList.add(item);
    }*/
}
