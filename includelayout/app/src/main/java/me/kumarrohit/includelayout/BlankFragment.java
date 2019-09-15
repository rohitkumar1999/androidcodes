package me.kumarrohit.includelayout;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class BlankFragment extends Fragment {

    TextView txt ;
    Button btn  ;

 int count = 0 ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Frag", "BlankFragment: ");

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
         btn = view.findViewById(R.id.btnPositive) ;
         txt = view.findViewById(R.id.txtPositive) ;
        Log.d("Frag", "BlankFragmentdeclared: ");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Log.d("Frag", "BlankFragmentdeclaredafter: ");

                count+=1 ;
                txt.setText(Integer.toString(count));
            }
        });
        Log.d("Frag", "BlankFragmentended: ");

        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}
