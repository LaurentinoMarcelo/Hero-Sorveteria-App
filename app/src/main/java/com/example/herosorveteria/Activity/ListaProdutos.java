package com.example.herosorveteria.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.herosorveteria.R;
import com.example.herosorveteria.menu.CadastroProdutoActivity;

public class ListaProdutos extends AppCompatActivity {
    Button btnCadastrarProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        iniciarComponentes();
    }

    public void cadastrarProduto(View v){
        Intent i = new Intent(this, CadastroProdutoActivity.class);
        startActivity(i);
        finish();
    }

    private void iniciarComponentes() {

        btnCadastrarProduto = findViewById(R.id.btnCadastrarProduto);

    }
}