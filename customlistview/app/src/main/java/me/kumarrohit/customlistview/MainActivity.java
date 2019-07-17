package me.kumarrohit.customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      listView = findViewById(R.id.list) ;


        Person neeraj = new Person("neeraj","18","male") ;
        Person neeraj1 = new Person("neeraj1","18","male") ;
        Person neeraj2 = new Person("neeraj2","18","male") ;
        Person neeraj3 = new Person("neeraj3","18","male") ;
        Person neeraj4 = new Person("neeraj4","18","male") ;
        Person neeraj5 = new Person("neeraj5","18","male") ;
        Person neeraj6 = new Person("neera6","18","male") ;
        Person neeraj7 = new Person("neeraj7","18","male") ;
        Person neeraj8 = new Person("neera8","18","male") ;


        ArrayList<Person> people =  new ArrayList<>() ;
        people.add(neeraj) ;
        people.add(neeraj1) ;
        people.add(neeraj2) ;
        people.add(neeraj3) ;
        people.add(neeraj4) ;
        people.add(neeraj5) ;
        people.add(neeraj6) ;
        people.add(neeraj7) ;
        people.add(neeraj8) ;

        PersonAdapter adapter = new PersonAdapter(
           getApplicationContext(),R.layout.row_layout_person,people
        );

        listView.setAdapter(adapter);












    }
}
