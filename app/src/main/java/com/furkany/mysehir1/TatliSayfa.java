package com.furkany.mysehir1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class TatliSayfa extends AppCompatActivity {

    FirebaseFirestore db_fy;
    ArrayList<String> fy_menuad;
    ArrayList<String> fy_menuaciklama;
    FirebaseStorage storage_fy;
    StorageReference streference_fy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tatli_sayfa);
        String i = getIntent().getStringExtra("itemno_fy");
        db_fy=FirebaseFirestore.getInstance();
        storage_fy=FirebaseStorage.getInstance();
        streference_fy=storage_fy.getReference();

        DocumentReference reference = db_fy.collection("YoreselTatlilar").document("Ia15pIyyx6OpYn34s3Vt");
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> menu = document.getData();
                    fy_menuad = (ArrayList<String>) menu.get("ad");
                    fy_menuaciklama =(ArrayList<String>) menu.get("aciklama");
                    if (document.exists()) {
                        getSupportActionBar().setTitle(fy_menuad.get(Integer.parseInt(i)));
                        TextView textView9 = findViewById(R.id.tatliBaslıkTxtView_fy);
                        textView9.setText(fy_menuad.get(Integer.parseInt(i)));
                        TextView textView = findViewById(R.id.tatliTxtView_fy);
                        textView.setText(fy_menuaciklama.get(Integer.parseInt(i)));
                        StorageReference refst_fy=streference_fy.child("Yoresel Tatlilar/Z"+i+".jpg");
                        try {
                            final File dosya= File.createTempFile("resim","jpg");
                            refst_fy.getFile(dosya).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap btmp= BitmapFactory.decodeFile(dosya.getAbsolutePath());
                                    ImageView imageView = findViewById(R.id.tatliImageView_fy);
                                    imageView.setImageBitmap(btmp);

                                }
                            });

                        }catch (IOException e) {

                            throw new RuntimeException(e);
                        }
                        if (i!=null){
                            TextView textView13 = findViewById(R.id.tatliTxtView_fy);
                            textView13.setText(fy_menuaciklama.get(Integer.parseInt(i)));
                        }
                    }
                }
            }
        });

    }
}