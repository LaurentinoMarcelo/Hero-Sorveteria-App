package com.example.herosorveteria.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.herosorveteria.R;
import com.example.herosorveteria.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;

public class Header extends AppCompatActivity {

    private TextView nomeHeaderCampo;
    private TextView cargoHeaderCampo;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header);

        inicializarComponetes();

        nomeHeaderCampo.setText("amrlmrofrog");




    }

    public void inicializarComponetes(){
        nomeHeaderCampo = findViewById(R.id.nameHeader);
        cargoHeaderCampo = findViewById(R.id.cargoHeader);

    }

}