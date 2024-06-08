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

public class YemeklerSayfa extends AppCompatActivity {
    FirebaseFirestore fy_db;
    ArrayList<String> fy_menuArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yemekler_sayfa);
        getSupportActionBar().setTitle("Yemekler");
        fy_db=FirebaseFirestore.getInstance();

        DocumentReference reference=fy_db.collection("YoreselYemekler").document("DjVIPclpkEBfz5tY6J4H");
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> menu = document.getData();
                    fy_menuArray = (ArrayList<String>) menu.get("ad");
                    if (document.exists()) {
                        ListView listView2_fy = findViewById(R.id.yemeklerListView);
                        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, fy_menuArray);
                        listView2_fy.setAdapter(adapter);

                        listView2_fy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(YemeklerSayfa.this, YemekSayfa.class);
                                intent.putExtra("itemno_fy", String.valueOf(i));
                                startActivity(intent);
                            }
                        });
                    }

                }

            }
        });


    }
}