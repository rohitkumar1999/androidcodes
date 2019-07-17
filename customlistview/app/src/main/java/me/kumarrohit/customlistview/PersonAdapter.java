package me.kumarrohit.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.net.ContentHandler;
import java.util.ArrayList;

public class PersonAdapter extends ArrayAdapter<Person> {

    Context mContext ;
    int mResource ;

    public PersonAdapter(Context context, int resource, ArrayList<Person> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName() ;
        String age = getItem(position).getAge() ;
        String gender = getItem(position).getSex() ;

        Person person = new Person(name,age,gender) ;

        LayoutInflater inflater = LayoutInflater.from(mContext) ;
        convertView = inflater.inflate(mResource,parent,false) ;

        TextView txtname = convertView.findViewById(R.id.name) ;
        TextView txtage = convertView.findViewById(R.id.age) ;
        TextView txtgender = convertView.findViewById(R.id.gender) ;


        txtname.setText(name);
        txtage.setText(age);
        txtgender.setText(gender);

        return  convertView ;
    }
}
