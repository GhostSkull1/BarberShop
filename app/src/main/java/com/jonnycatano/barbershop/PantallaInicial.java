package com.jonnycatano.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaInicial extends AppCompatActivity {

    Button registro, inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

    }

    public void registro(View view){
        Intent registro = new Intent(this,RegistroActivity.class);
        startActivity(registro);
    }

    public void iniciar(View view){
        Intent iniciar = new Intent(this,IniciarActivity.class);
        startActivity(iniciar);
    }
}