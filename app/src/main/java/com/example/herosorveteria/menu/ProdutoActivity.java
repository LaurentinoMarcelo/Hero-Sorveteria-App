package com.example.herosorveteria.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.herosorveteria.R;
import com.example.herosorveteria.adapter.AdapterMovimentacao;
import com.example.herosorveteria.adapter.AdapterMovimentacaoProdutos;
import com.example.herosorveteria.cadastro.CadastroProdutoActivity;
import com.example.herosorveteria.model.MovimentacaoProdutos;
import com.example.herosorveteria.model.MovimentacaoReceitas;

import java.util.ArrayList;
import java.util.List;


public class ProdutoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterMovimentacaoProdutos adapterMovimentacaoProdutos;
    private List<MovimentacaoProdutos> movimentacaoProdutos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        inicializarComponentes();

        //Configurar adpter
        AdapterMovimentacaoProdutos adapter = new AdapterMovimentacaoProdutos(movimentacaoProdutos,this);


        //Configurar RecicleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }

    public void cadastrarProduto(View v){
        Intent i = new Intent(this, CadastroProdutoActivity.class);
        startActivity(i);
        finish();
    }

    public void inicializarComponentes(){
        recyclerView = findViewById(R.id.recyclerViewListProdutos);

    }
}