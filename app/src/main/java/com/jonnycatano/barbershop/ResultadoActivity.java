package com.jonnycatano.barbershop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ResultadoActivity extends AppCompatActivity {

    TextView etnombre, etcelular, etbarbero, etfecha, ethora;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        etnombre = findViewById(R.id.nombrecliente);
        etcelular = findViewById(R.id.numerodecelular);
        etbarbero = findViewById(R.id.nombrebarbero);
        etfecha = findViewById(R.id.fechacita);
        ethora = findViewById(R.id.horacita);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                etnombre.setText(documentSnapshot.getString("fName"));
                etcelular.setText(documentSnapshot.getString("Phone"));
                etbarbero.setText(documentSnapshot.getString("Barber"));
                etfecha.setText(documentSnapshot.getString("Fecha"));
                ethora.setText(documentSnapshot.getString("Hora"));

            }
        });
    }

    public void regresar(View view){
        Intent regresar = new Intent(this, PrincipalActivity.class);
        startActivity(regresar);
        finish();
}
}