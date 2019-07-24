package me.kumarrohit.datepicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        List<person> list = new ArrayList<>() ;
        list.add(new person("neeraj","pandey")) ;
        list.add(new person("rohit","kumar")) ;
        list.add(new person("vimal","tripathi")) ;
        list.add(new person("neeraj","pandey")) ;
        list.add(new person("rohit","kumar")) ;
        list.add(new person("vimal","tripathi")) ;
        list.add(new person("neeraj","pandey")) ;
        list.add(new person("rohit","kumar")) ;
        list.add(new person("vimal","tripathi")) ;

        Adapter adapter = new Adapter(list) ;
        recyclerView.setAdapter(adapter);

    }
}
