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

public class TarihiYerlerSayfa extends AppCompatActivity {

    FirebaseFirestore db_fy;
    ArrayList<String> fy_menuArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarihi_yerler_sayfa);
        getSupportActionBar().setTitle("Tarihi Yerler");
        db_fy = FirebaseFirestore.getInstance();

        DocumentReference reference=db_fy.collection("TarihiYerler").document("VUusZ0HZjVzejA8TAIsB");
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> menu = document.getData();
                    fy_menuArray = (ArrayList<String>) menu.get("ad");
                    if (document.exists()) {
                        ListView listView1_fy = findViewById(R.id.tarihiYerlerListView);
                        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, fy_menuArray);
                        listView1_fy.setAdapter(adapter);
                        listView1_fy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(TarihiYerlerSayfa.this, TarihiYerSayfa.class);
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