package com.example.herosorveteria.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFireBase {

    private static FirebaseAuth autenticacao;

    public static FirebaseAuth getFireBaseAutenticacao(){
        if( autenticacao == null ){
            autenticacao = FirebaseAuth.getInstance();
        }return autenticacao;
    }
}
