package com.example.herosorveteria.model;

import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Clientes {

    private String nome;
    private String telefone;
    private String redeSocial;
    private String Key;

    public void salvar(){
        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());

        DatabaseReference fireBase = ConfiguracaoFireBase.getFirebaseDatabase();
        fireBase.child("clientes")
                .child(idUsuario)
                .push()
                .setValue(this);
    }

    public String getRedeSocial() {
        return redeSocial;
    }

    public void setRedeSocial(String redeSocial) {
        this.redeSocial = redeSocial;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}

