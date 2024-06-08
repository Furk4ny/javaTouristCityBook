package com.furkany.mysehir1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
FirebaseFirestore fy_db;
ArrayList<String> fy_menuArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fy_db = FirebaseFirestore.getInstance();
        DocumentReference reference = fy_db.collection("AnaMenu").document("LLN8A2zfmGrQvVVqa4DV");
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> menu = document.getData();
                    fy_menuArray = (ArrayList<String>) menu.get("AnaMenu");
                    if (document.exists()) {
                        ListView listView_fy = findViewById(R.id.mainListView);
                        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, fy_menuArray);
                        listView_fy.setAdapter(adapter);

                        listView_fy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                switch (i) {
                                    case 0:
                                        startActivity(new Intent(MainActivity.this, TarihceSayfa.class));
                                        break;
                                    case 1:
                                        startActivity(new Intent(MainActivity.this, TarihiYerlerSayfa.class));
                                        break;
                                    case 2:
                                        startActivity(new Intent(MainActivity.this, YemeklerSayfa.class));
                                        break;
                                    case 3:
                                        startActivity(new Intent(MainActivity.this, TatlilarSayfa.class));
                                        break;
                                }

                            }
                        });
                    }
                }
            }
        });

        getSupportActionBar().setTitle("Ana Menü");




    }
}