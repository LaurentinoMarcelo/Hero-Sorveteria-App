package com.example.herosorveteria.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.herosorveteria.R;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.google.firebase.auth.FirebaseAuth;

public class IniciarEverificar extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_everificar);


        iniciarAnimacao();

        verificarUsuarioLogado();
    }

    private void iniciarAnimacao() {
        ImageView imageView = findViewById(R.id.logoView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        imageView.startAnimation(animation);
    }

    public void verificarUsuarioLogado(){

        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        if( autenticacao.getCurrentUser() != null ){
            abrirTelaPrincipal();
        }else {
            abrirTelaLogin();
        }
    }

    public void abrirTelaPrincipal(){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
    public void abrirTelaLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }


}