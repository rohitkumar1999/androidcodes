package me.kumarrohit.list;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView    list = findViewById(R.id.listview) ;

        Person rohit = new Person("rohit","11-10-1999","male") ;
        Person abhishek = new Person("abhishek","23-09-1999","male") ;
        Person armaan = new Person("armaan","17-11-2004","male");
        Person raj = new Person("raj","10-08-2003","male") ;
        Person himanshu = new Person("himanshu","23-03-2005","male") ;
        Person lucky = new Person("lucky","25-01-2004","male")  ;
        Person rohtesh = new Person("rohtesh","10-03-2004","male") ;

            ArrayList<Person>  people = new ArrayList<>() ;

            people.add(rohit);
            people.add(abhishek) ;
            people.add(armaan) ;
            people.add(raj) ;
            people.add(himanshu);
            people.add(lucky) ;
            people.add(rohtesh) ;


           PeopleAdapter adapter = new PeopleAdapter(this,R.layout.personlayout,people) ;
           list.setAdapter(adapter);


    }
}
