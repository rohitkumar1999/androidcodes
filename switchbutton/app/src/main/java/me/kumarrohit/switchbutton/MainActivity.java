package me.kumarrohit.switchbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Switch sw   ;
    Button btn  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sw = findViewById(R.id.button) ;
        btn = findViewById(R.id.btn) ;
         btn.setOnClickListener(new View.OnClickListener() {
             String str1 ;
             @Override
             public void onClick(View view) {
                 if(sw.isChecked())
                 {
                  str1 = sw.getTextOn().toString()   ;
                 }
                 else
                     str1=sw.getTextOff().toString() ;

                 Toast.makeText(getApplicationContext(),str1,Toast.LENGTH_LONG).show();

             }
         });
    }
}
