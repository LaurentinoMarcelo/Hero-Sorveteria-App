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
import com.example.herosorveteria.adapter.AdapterClientes;
import com.example.herosorveteria.adapter.AdapterProduto;
import com.example.herosorveteria.cadastro.CadastroClienteActivity;
import com.example.herosorveteria.cadastro.CadastroFornecedorActivity;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.helper.Base64Custom;
import com.example.herosorveteria.model.Clientes;
import com.example.herosorveteria.model.Produto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListaClientesActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCliente;
    private AdapterClientes adapterClientes;
    private List<Clientes> clientList = new ArrayList<>();
    private Clientes cliente;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private DatabaseReference clienteRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        inicializarComponentes();
        swipe();

        adapterClientes = new AdapterClientes(clientList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCliente.setLayoutManager(layoutManager);
        recyclerViewCliente.setHasFixedSize(true);
        recyclerViewCliente.setAdapter(adapterClientes);
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
                excluirClientes(viewHolder);
            }
        };
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerViewCliente);
    }

    private void excluirClientes(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Excluir, Clientes da Lista");
        alertDialog.setMessage("Você tem certeza que deseja excluir esse cliente?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                cliente = clientList.get(position);

                String emialUsuario = autenticacao.getCurrentUser().getEmail();
                String idUsuario = Base64Custom.codificarBase64(emialUsuario);
                clienteRef = firebaseRef.child("cliestes")
                        .child(idUsuario);
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ListaClientesActivity.this, "Cancelado",
                        Toast.LENGTH_SHORT).show();
                adapterClientes.notifyDataSetChanged();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void inicializarComponentes() {
        recyclerViewCliente = findViewById(R.id.recyclerListClientes);
    }

    public void cadastraClientes(View v){

        Intent i = new Intent(this, CadastroClienteActivity.class);
        startActivity(i);
        finish();

    }
}