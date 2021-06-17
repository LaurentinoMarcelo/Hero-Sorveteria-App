package com.example.herosorveteria.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.herosorveteria.R;
import com.example.herosorveteria.cadastro.CadastroFornecedorActivity;

public class ListaFornecedor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_fornecedor);
    }

    public void cadastraFornecedor(View v){

        Intent i = new Intent(this, CadastroFornecedorActivity.class);
        startActivity(i);
        finish();

    }
}