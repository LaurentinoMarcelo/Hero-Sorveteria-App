package com.example.herosorveteria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText UsuarioLogin;
    EditText SenhaLogin;
    Button Entrar;
    Button CadastrarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

    }

    void inicializarComponentes() {

        UsuarioLogin = findViewById(R.id.emailLogin);
        SenhaLogin = findViewById(R.id.senhaLogin);
        Entrar = findViewById(R.id.botaoLogin);
        CadastrarConta = findViewById(R.id.criarConta);

    }

    public void logar(View v) {
        Intent i = new Intent(this, MenuLateral.class);
        startActivity(i);
    }
}