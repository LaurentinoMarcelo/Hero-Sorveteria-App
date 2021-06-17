package com.example.herosorveteria.model;

import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private Double receita = 0.00;
    private Double despesas = 0.00;
    private String idUsuario;


    public Usuario(){

    }
    public void salvar(){
        DatabaseReference firebase = ConfiguracaoFireBase.getFirebaseDatabase();
        firebase.child("usuarios")
                .child(this.idUsuario)
                .setValue(this);
    }

    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Double getReceita() {
        return receita;
    }

    public void setReceita(Double receita) {
        this.receita = receita;
    }

    public Double getDespesas() {
        return despesas;
    }

    public void setDespesas(Double despesas) {
        this.despesas = despesas;
    }
}
