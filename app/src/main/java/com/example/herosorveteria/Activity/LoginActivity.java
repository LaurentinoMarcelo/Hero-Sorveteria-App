package com.example.herosorveteria.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.herosorveteria.R;

public class LoginActivity extends AppCompatActivity {

    //private FirebaseAuth usuario = FirebaseAuth.getInstance();
    EditText UsuarioLogin;
    EditText SenhaLogin;
    Button Entrar;
    Button CadastrarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();

       // usuario.createUserWithEmailAndPassword("");
    }

    void inicializarComponentes() {

        UsuarioLogin = findViewById(R.id.emailLogin);
        SenhaLogin = findViewById(R.id.senhaLogin);
        Entrar = findViewById(R.id.botaoLogin);
        CadastrarConta = findViewById(R.id.criarConta);

    }

    public void logar(View v) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }

    public void btnCadastrar(View v){
        Intent i = new Intent(this, CadastroUsuario.class);
        startActivity(i);
    }
}