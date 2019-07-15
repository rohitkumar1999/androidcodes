package me.kumarrohit.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PeopleAdapter extends ArrayAdapter<Person> {

    private  Context mContext ;
    int mResource ;

    public PeopleAdapter(Context context, int resource, ArrayList<Person> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName() ;
        String dob = getItem(position).getDob() ;
        String gender = getItem(position).getSex()  ;

        Person person = new Person(name,dob,gender) ;

        LayoutInflater inflater = LayoutInflater.from(mContext) ;
        convertView = inflater.inflate(mResource,parent,false) ;

        TextView tvname = (TextView)convertView.findViewById(R.id.textView1) ;
        TextView tvdob = (TextView)convertView.findViewById(R.id.textView2) ;
        TextView tvsex = (TextView)convertView.findViewById(R.id.textView3) ;

        tvname.setText(name);
        tvdob.setText(dob);
        tvsex.setText(gender);

        return convertView ;

    }
}
