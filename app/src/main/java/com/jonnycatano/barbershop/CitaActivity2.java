package com.jonnycatano.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CitaActivity2 extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText Etfecha, Ethora, Etbarbero,etnombre, etcorreo;
    Button btnregistrocita;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita2);

        Etbarbero = findViewById(R.id.etnombarbero);
        Etfecha = findViewById(R.id.etfecha);
        Ethora = findViewById(R.id.ethora);
        btnregistrocita = findViewById(R.id.btnregistrarcita);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), ResultadoActivity.class));
            finish();
        }

        btnregistrocita.setOnClickListener((v) -> {
            String barber = Etbarbero.getText().toString();
            String fecha = Etfecha.getText().toString().trim();
            String hora = Ethora.getText().toString().trim();
            String email = etcorreo.getText().toString().trim();
            String password = etcorreo.getText().toString().trim();


            if (TextUtils.isEmpty(barber)){
                Etbarbero.setError("se requiere el nombre del barbero");
                return;
            }

            if (TextUtils.isEmpty(fecha)){
                Etfecha.setError("se requiere una fecha");
                return;
            }

            if (TextUtils.isEmpty(hora)){
                Ethora.setError("se requiere una hora");
                return;
            }

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((task) -> {

                if (task.isSuccessful()){
                    Toast.makeText(CitaActivity2.this, "User Created", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String, Object> users = new HashMap<>();
                    users.put("Barber",barber);
                    users.put("Fecha",fecha);
                    users.put("Hora",hora);
                    documentReference.set(users).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                        Log.d(TAG, "onSuccess: perfil de usuario creado por " + userID);
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });
                    startActivity(new Intent(getApplicationContext(), ResultadoActivity.class));
                }else {
                    Toast.makeText(CitaActivity2.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    });

    //public void volver(View view){
    //    Intent resultado = new Intent(this,CitaActivity2.class);
    //    startActivity(resultado);
    }
}