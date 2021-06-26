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
import com.example.herosorveteria.adapter.AdapterFornecedores;
import com.example.herosorveteria.adapter.AdapterProduto;
import com.example.herosorveteria.cadastro.CadastroFornecedorActivity;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.helper.Base64Custom;
import com.example.herosorveteria.model.Fornecedor;
import com.example.herosorveteria.model.Produto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListaFornecedorActivity extends AppCompatActivity {
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    RecyclerView recyclerViewFornecedor;
    private ValueEventListener valueEventListener;
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private DatabaseReference fornecedorRef;
    private AdapterFornecedores adapterFornecedores;
    private List<Fornecedor> fornecedorList = new ArrayList<>();
    private Fornecedor fornecedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_fornecedor);

        inicializarComponentes();
        swipe();

        adapterFornecedores = new AdapterFornecedores(fornecedorList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewFornecedor.setLayoutManager(layoutManager);
        recyclerViewFornecedor.setHasFixedSize(true);
        recyclerViewFornecedor.setAdapter(adapterFornecedores);
    }

    private void swipe() {
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
                excluirFornecedores(viewHolder);
            }
        };
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerViewFornecedor);
    }

    private void excluirFornecedores(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Excluir, Fornecedor da Lista");
        alertDialog.setMessage("Você tem certeza que deseja excluir esse fornecedor?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                fornecedor = fornecedorList.get(position);

                String emialUsuario = autenticacao.getCurrentUser().getEmail();
                String idUsuario = Base64Custom.codificarBase64(emialUsuario);
                fornecedorRef= firebaseRef.child("produtos")
                        .child(idUsuario);

                fornecedorRef.child(fornecedor.getKey()).removeValue();
                adapterFornecedores.notifyItemRemoved(position);
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ListaFornecedorActivity.this, "Cancelado",
                        Toast.LENGTH_SHORT).show();
                adapterFornecedores.notifyDataSetChanged();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void inicializarComponentes() {
        recyclerViewFornecedor = findViewById(R.id.recyclerListFornecedor);
    }

    public void cadastraFornecedor(View v){

        Intent i = new Intent(this, CadastroFornecedorActivity.class);
        startActivity(i);
        finish();

    }

    private void recuperarFornecedores() {

        String emialUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emialUsuario);
        fornecedorRef = firebaseRef.child("fornecedores")
                .child(idUsuario);
        valueEventListener = fornecedorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                fornecedorList.clear();

                for (DataSnapshot dados: snapshot.getChildren()){
                    Fornecedor fornecedor = dados.getValue(Fornecedor.class);
                    fornecedor.setKey(dados.getKey());
                    fornecedorList.add(fornecedor);
                }
                adapterFornecedores.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        recuperarFornecedores();
    }


}