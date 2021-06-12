package com.example.herosorveteria.model;

import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.helper.Base64Custom;
import com.example.herosorveteria.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Produtos {

    String nomeProduto;
    String valorProduto;
    String valorVenda;
    String categoriaProduto;
    String quantidadeProduto;
    String unidadeProduto;

    public Produtos() {

    }

    public void salvar(){
        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());

        DatabaseReference fireBase = ConfiguracaoFireBase.getDatabaseReference();
        fireBase.child("produtos")
                .child(idUsuario)
                .push()
                .setValue(this);
    }

    public String getUnidadeProduto() {
        return unidadeProduto;
    }

    public void setUnidadeProduto(String unidadeProduto) {
        this.unidadeProduto = unidadeProduto;
    }

    public String getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(String valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getNomeProduto() {

        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {

        this.nomeProduto = nomeProduto;
    }

    public String getValorProduto() {

        return valorProduto;
    }

    public void setValorProduto(String valorProduto) {

        this.valorProduto = valorProduto;
    }


    public String getCategoriaProduto() {

        return categoriaProduto;
    }

    public void setCategoriaProduto(String categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public String getQuantidadeProduto() {

        return quantidadeProduto;
    }

    public void setQuantidadeProduto(String quantidadeProduto) {

        this.quantidadeProduto = quantidadeProduto;
    }
}
