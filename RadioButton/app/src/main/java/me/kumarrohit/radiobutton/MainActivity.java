package me.kumarrohit.radiobutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn ;
    RadioGroup radiogroupgender ;
    RadioButton button  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn)  ;
        radiogroupgender = findViewById(R.id.radiogender) ;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedid = radiogroupgender.getCheckedRadioButtonId() ;
                button = findViewById(selectedid) ;
                Toast.makeText(getApplicationContext(),button.getText(),Toast.LENGTH_LONG).show(); ;
            }
        });
    }
}
