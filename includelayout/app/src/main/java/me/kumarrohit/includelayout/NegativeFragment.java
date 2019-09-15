package me.kumarrohit.includelayout;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NegativeFragment extends Fragment implements View.OnClickListener {

    Integer count = 0 ;
    TextView txt1 ;
    Button btn1  ;

    public NegativeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Frag", "NegativeFragment: ");

        View view = inflater.inflate(R.layout.fragment_negative, container, false);
         btn1 = view.findViewById(R.id.btnNegative) ;
          txt1 = view.findViewById(R.id.txtNegative) ;
        Log.d("Frag", "NegativeFragmentdeclared: ");
      btn1.setOnClickListener(this);
        Log.d("Frag", "NegativeFragmentended: ");

        return inflater.inflate(R.layout.fragment_negative, container, false);
    }

    @Override
    public void onClick(View view1) {
        Log.d("Frag", "NegativeFragmentdeclaredafter: ");

        count=count-1;
    txt1.setText(count.toString());
    Person p = new Person("rohit",19,8826336183) ;
    }
}
