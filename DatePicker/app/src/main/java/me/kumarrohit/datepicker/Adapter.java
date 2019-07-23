package me.kumarrohit.datepicker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> {

    private List<person> personList  ;

    public Adapter(List<person> personList) {
        this.personList = personList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout,parent,false) ;
        return new viewholder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        String fname = personList.get(position).getFirstname() ;
        String lname = personList.get(position).getLastname() ;
        holder.setData(fname,lname);

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    class viewholder extends RecyclerView.ViewHolder
    {
        TextView fname ;
        TextView Lname ;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.text1) ;
            Lname = itemView.findViewById(R.id.text2) ;

        }
        public void setData(String firstname,String  lastname)
        {
            fname.setText(firstname);
            Lname.setText(lastname);
        }

    }

}
