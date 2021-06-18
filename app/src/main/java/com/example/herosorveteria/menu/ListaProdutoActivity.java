package com.example.herosorveteria.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.herosorveteria.R;
import com.example.herosorveteria.adapter.AdapterProduto;
import com.example.herosorveteria.cadastro.CadastroProdutoActivity;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.helper.Base64Custom;
import com.example.herosorveteria.model.Produto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ListaProdutoActivity extends AppCompatActivity {
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    RecyclerView recyclerViewProdutos;
    private ValueEventListener valueEventListener;
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private DatabaseReference produtoRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private AdapterProduto adapterProduto;
    private List<Produto> produtoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produto);

        inicializarComponentes();

        adapterProduto = new AdapterProduto(produtoList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProdutos.setLayoutManager(layoutManager);
        recyclerViewProdutos.setHasFixedSize(true);
        recyclerViewProdutos.setAdapter(adapterProduto);

    }

    public void recuperarProdutos(){
        String emialUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emialUsuario);
        produtoRef = firebaseRef.child("produtos")
                .child(idUsuario);

        valueEventListener = produtoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                produtoList.clear();

                for(DataSnapshot dados: snapshot.getChildren()){
                    Produto produto = dados.getValue(Produto.class);
                    produto.setKey(dados.getKey());
                    produtoList.add(produto);
                }
                adapterProduto.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    public void cadastrarProduto(View v){
        Intent i = new Intent(this, CadastroProdutoActivity.class);
        startActivity(i);
        finish();
    }

    public void inicializarComponentes(){

        recyclerViewProdutos = findViewById(R.id.recyclerListaProdutos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarProdutos();
    }


}