package com.example.herosorveteria.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.herosorveteria.R;
import com.google.android.material.textfield.TextInputEditText;

public class Despesas extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao, campoValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);


    }
    public void inicializarComponentes(){
        campoData = findViewById(R.id.data);
        campoCategoria = findViewById(R.id.categoria);
        campoDescricao = findViewById(R.id.descricao);
        
    }
}