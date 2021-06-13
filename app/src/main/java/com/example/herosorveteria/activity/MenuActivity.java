package com.example.herosorveteria.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.example.herosorveteria.R;
import com.example.herosorveteria.adapter.AdapterMovimentacao;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.helper.Base64Custom;
import com.example.herosorveteria.menu.DespesasActivity;
import com.example.herosorveteria.menu.HistoricoDeVendasActivity;
import com.example.herosorveteria.menu.ListadeClientesActivity;
import com.example.herosorveteria.menu.ProdutoActivity;
import com.example.herosorveteria.menu.ReceitaActivity;
import com.example.herosorveteria.model.MovimentacaoReceitas;
import com.example.herosorveteria.model.Usuario;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MaterialCalendarView calendarView;
    private TextView textoSaudacao, textoSaldo;
    private Double despesaTotal = 0.00;
    private Double receitaTotal = 0.0;
    private Double resumoUsuario = 0.0;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getDatabaseReference();
    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovimentacao;

    private RecyclerView recyclerView;
    private AdapterMovimentacao adapterMovimentacao;
    private List<MovimentacaoReceitas> movimentacoes = new ArrayList<>();
    private DatabaseReference movimentacaoRef;
    private String mesAnoSelecionado;
    private MovimentacaoReceitas movimentacaoReceitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        inicializarComponetes();
        swipe();

        adapterMovimentacao = new AdapterMovimentacao(movimentacoes, this);

        //Configurar RecicleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterMovimentacao);
        //Toolbar

        setSupportActionBar(toolbar);


        //Navigation Menu

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MenuActivity.this,
                                drawerLayout,
                                toolbar,
                                R.string.open,
                                R.string.close );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    public void swipe(){

        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
                int dragsFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START|ItemTouchHelper.END;
                return makeMovementFlags(dragsFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirMovimentos(viewHolder);
            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);

    }
     public void excluirMovimentos(RecyclerView.ViewHolder viewHolder){
         AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
         alertDialog.setTitle("Excluir, Movimentação da Conta");
         alertDialog.setMessage("Você tem certeza que deseja excluir essa movimentação?");
         alertDialog.setCancelable(false);

         alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                movimentacaoReceitas = movimentacoes.get(position);

                 String emialUsuario = autenticacao.getCurrentUser().getEmail();
                 String idUsuario = Base64Custom.codificarBase64(emialUsuario);
                 movimentacaoRef = firebaseRef.child("movimentacao")
                         .child(idUsuario)
                         .child( mesAnoSelecionado );

                 movimentacaoRef.child(movimentacaoReceitas.getKey()).removeValue();
                 adapterMovimentacao.notifyItemRemoved(position);
                 atualizarSaldo();
             }
         });
         alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 Toast.makeText(MenuActivity.this, "Cancelado",
                         Toast.LENGTH_SHORT).show();
                 adapterMovimentacao.notifyDataSetChanged();

             }
         });
         AlertDialog alert = alertDialog.create();
         alert.show();
     }

    public void atualizarSaldo(){

        String emialUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emialUsuario);
        usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        if( movimentacaoReceitas.getTipo().equals("r")){
            receitaTotal = receitaTotal - movimentacaoReceitas.getValor();
            usuarioRef.child("receita").setValue(receitaTotal);
        }

    }
    public void recuperarMovimentacoes(){

        String emialUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emialUsuario);
        movimentacaoRef = firebaseRef.child("movimentacao")
                                     .child(idUsuario)
                                     .child( mesAnoSelecionado );

        valueEventListenerMovimentacao = movimentacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                movimentacoes.clear();

                for(DataSnapshot dados: snapshot.getChildren()){
                    MovimentacaoReceitas movimentaco = dados.getValue(MovimentacaoReceitas.class);
                    movimentaco.setKey(dados.getKey());
                    movimentacoes.add(movimentaco);
                }

                adapterMovimentacao.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void recuperarResumo() {

        String emialUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emialUsuario);
        usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesas();
                receitaTotal = usuario.getReceita();
                resumoUsuario = receitaTotal - despesaTotal;

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String resultadoFormatado = decimalFormat.format(resumoUsuario);

                textoSaudacao.setText("Olá, " + usuario.getNome());
                textoSaldo.setText("R$ " + resultadoFormatado);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void inicializarComponetes() {

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);

        calendarView = findViewById(R.id.calendarView);
        configuraCalendarView();

        textoSaldo = findViewById(R.id.textSaldo);
        textoSaudacao = findViewById(R.id.textSaudacao);

        recyclerView = findViewById(R.id.recyclerMovimentos);
    }

    private void configuraCalendarView() {
        CharSequence meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarView.setTitleMonths(meses);

        CalendarDay dataAtual = calendarView.getCurrentDate();
        String mesSelecionado = String.format("%02d", (dataAtual.getMonth() + 1) );
        mesAnoSelecionado = String.valueOf( mesSelecionado+ "" + dataAtual.getYear());

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String mesSelecionado = String.format("%02d", (date.getMonth() + 1) );
                mesAnoSelecionado = String.valueOf(mesSelecionado + "" + date.getYear());

                movimentacaoRef.removeEventListener(valueEventListenerMovimentacao);
                recuperarMovimentacoes();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_EstoqueProduto:
                Intent cp = new Intent(this, ProdutoActivity.class);
                startActivity(cp);
                break;
            case R.id.nav_calculadoraTroco:
                Intent ct = new Intent(this, ReceitaActivity.class);
                startActivity(ct);
                break;
            case R.id.nav_historicoVendas:
                Intent hv = new Intent(this, HistoricoDeVendasActivity.class);
                startActivity(hv);
                break;
            case R.id.nav_contasPagar:
                Intent dP = new Intent(this, DespesasActivity.class);
                startActivity(dP);
                break;
            case R.id.nav_listaClientes:
                Intent lC = new Intent(this, ListadeClientesActivity.class);
                startActivity(lC);
                break;

            case R.id.nav_sair:
                autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
                autenticacao.signOut();
                Intent lg = new Intent(this, LoginActivity.class);
                startActivity(lg);
                finish();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarResumo();
        recuperarMovimentacoes();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioRef.removeEventListener(valueEventListenerUsuario);
        movimentacaoRef.removeEventListener(valueEventListenerMovimentacao);
    }
}