package com.example.herosorveteria.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
    private DatabaseReference produtoRef;
    private AdapterProduto adapterProduto;
    private List<Produto> produtoList = new ArrayList<>();
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produto);

        inicializarComponentes();
        swipe();

        adapterProduto = new AdapterProduto(produtoList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProdutos.setLayoutManager(layoutManager);
        recyclerViewProdutos.setHasFixedSize(true);
        recyclerViewProdutos.setAdapter(adapterProduto);

    }

    public void swipe(){

        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
                int dragsFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swiperFlags = ItemTouchHelper.START|ItemTouchHelper.END;
                return makeMovementFlags(dragsFlags, swiperFlags);
            }

            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirProduto(viewHolder);
            }
        };
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerViewProdutos);
    }

    private void excluirProduto(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Excluir, Produto da Lista");
        alertDialog.setMessage("Você tem certeza que deseja excluir esse produto?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                produto = produtoList.get(position);

                String emialUsuario = autenticacao.getCurrentUser().getEmail();
                String idUsuario = Base64Custom.codificarBase64(emialUsuario);
                produtoRef = firebaseRef.child("produtos")
                            .child(idUsuario);


                produtoRef.child(produto.getKey()).removeValue();
                adapterProduto.notifyItemRemoved(position);
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ListaProdutoActivity.this, "Cancelado",
                        Toast.LENGTH_SHORT).show();
                adapterProduto.notifyDataSetChanged();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
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