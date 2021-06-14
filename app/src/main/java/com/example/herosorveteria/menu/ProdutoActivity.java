package com.example.herosorveteria.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.herosorveteria.R;
import com.example.herosorveteria.adapter.AdapterListaProdutos;
import com.example.herosorveteria.cadastro.CadastroProdutoActivity;
import com.example.herosorveteria.model.MovimentacaoProdutos;

import java.util.ArrayList;
import java.util.List;


public class ProdutoActivity extends AppCompatActivity {

    private List<MovimentacaoProdutos> listaProdutos = new ArrayList<>();
    private AdapterListaProdutos adapterListaProdutos;
    RecyclerView recyclerViewlistaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        inicializarComponentes();

        //Configurar adpter
        adapterListaProdutos = new AdapterListaProdutos(listaProdutos, this);


        //Configurar RecicleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewlistaProdutos.setLayoutManager(layoutManager);
        recyclerViewlistaProdutos.setHasFixedSize(true);
        recyclerViewlistaProdutos.setAdapter(adapterListaProdutos);

    }

    public void cadastrarProduto(View v){
        Intent i = new Intent(this, CadastroProdutoActivity.class);
        startActivity(i);
        finish();
    }

    public void inicializarComponentes(){
        recyclerViewlistaProdutos = findViewById(R.id.recyclerViewListProdutos);

    }
}