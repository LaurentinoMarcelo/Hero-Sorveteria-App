package com.example.herosorveteria.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.herosorveteria.R;
import com.example.herosorveteria.helper.DateCustom;
import com.example.herosorveteria.model.Movimentacao;
import com.google.android.material.textfield.TextInputEditText;

public class Despesas extends AppCompatActivity {

    TextInputEditText campoData, campoCategoria, campoDescricao, campoValor;
    private Movimentacao movimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        inicializarComponentes();

        campoData.setText(DateCustom.dataAtual());
    }

    private void inicializarComponentes() {
        campoData = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);

    }

    public void salvarDespesas(View v){

        String dataDespesa = campoData.getText().toString();

        movimentacao = new Movimentacao();
        movimentacao.setValor( Double.parseDouble((campoValor.getText().toString())));
        movimentacao.setCategoria(campoCategoria.getText().toString());
        movimentacao.setDescricao(campoDescricao.getText().toString());
        movimentacao.setData(campoData.getText().toString());
        movimentacao.setTipo("d");

        movimentacao.salvar(dataDespesa);
    }


}