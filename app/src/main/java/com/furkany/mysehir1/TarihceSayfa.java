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

public class TarihceSayfa extends AppCompatActivity {

    TextView text_fy;
    FirebaseFirestore db_fy;
    FirebaseStorage storage_fy;
    StorageReference streference_fy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarihce_sayfa);
        getSupportActionBar().setTitle("Tarihçe");
        text_fy=findViewById(R.id.textView1);
        db_fy=FirebaseFirestore.getInstance();
        storage_fy= FirebaseStorage.getInstance();
        streference_fy=storage_fy.getReference();

        DocumentReference reference_ycm = db_fy.collection("Tarihce").document("c56IXGFtsqsGjZFr8yil");
        reference_ycm.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document_fy = task.getResult();
                    text_fy.setText(document_fy.get("tarihce").toString());
                }
            }
        });
        StorageReference refst_fy=streference_fy.child("trabzon"+".jpg");
        try {
            final File dosya= File.createTempFile("resim","jpg");
            refst_fy.getFile(dosya).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap btmp= BitmapFactory.decodeFile(dosya.getAbsolutePath());
                    ImageView imageView = findViewById(R.id.imageView2);
                    imageView.setImageBitmap(btmp);

                }
            });

        }catch (IOException e) {

            throw new RuntimeException(e);
        }

    }
}