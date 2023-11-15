package com.jonnycatano.barbershop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PrincipalActivity extends AppCompatActivity {

    Button Agendar;

    TextView etnombre, etcelular;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        etnombre = findViewById(R.id.perfilnombre);
        etcelular = findViewById(R.id.perfilcelular);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                etnombre.setText(documentSnapshot.getString("fName"));
                etcelular.setText(documentSnapshot.getString("Phone"));

            }
        });

    }

    public void cerrar(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),IniciarActivity.class));
        finish();
    }

    public void Agendar(View view){
        Intent Agendar = new Intent(this, CitaActivity2.class);
        startActivity(Agendar);
        finish();
    }

}