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
import com.example.herosorveteria.cadastro.CadastroDespesasActivity;
import com.example.herosorveteria.menu.HistoricoDeVendasActivity;
import com.example.herosorveteria.menu.ListaClientesActivity;
import com.example.herosorveteria.menu.ListaFornecedorActivity;
import com.example.herosorveteria.menu.ListaProdutoActivity;
import com.example.herosorveteria.cadastro.CadastroReceitaActivity;
import com.example.herosorveteria.model.Movimentacao;
import com.example.herosorveteria.model.Usuario;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
    private TextView textoSaudacao, textoSaldo, nameHeader;
    private Double despesaTotal = 0.0;
    private Double receitaTotal = 0.0;
    private Double resumoUsuario;


    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovimentacao;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private AdapterMovimentacao adapterMovimentacao;
    private List<Movimentacao> movimentacoes = new ArrayList<>();
    private Movimentacao movimentacao;
    private DatabaseReference movimentacaoRef;
    private String mesAnoSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity_menu);

        inicializarComponetes();
        recuperarResumo();
        configuraCalendarView();
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
         alertDialog.setTitle("Excluir, Movimenta????o da Conta");
         alertDialog.setMessage("Voc?? tem certeza que deseja excluir essa movimenta????o?");
         alertDialog.setCancelable(false);

         alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                movimentacao = movimentacoes.get(position);

                 String emialUsuario = autenticacao.getCurrentUser().getEmail();
                 String idUsuario = Base64Custom.codificarBase64(emialUsuario);
                 movimentacaoRef = firebaseRef.child("movimentacao")
                         .child(idUsuario)
                         .child( mesAnoSelecionado );

                 movimentacaoRef.child(movimentacao.getKey()).removeValue();
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

        if( movimentacao.getTipo().equals("r")){
            receitaTotal = receitaTotal - movimentacao.getValor();
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
                    Movimentacao movimentaco = dados.getValue(Movimentacao.class);
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
        usuarioRef = firebaseRef.child("usuarios")
                    .child(idUsuario);

        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {

                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                    despesaTotal = usuario.getDespesaTotal();
                    receitaTotal = usuario.getReceitaTotal();
                    resumoUsuario = receitaTotal - despesaTotal;


                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String resultadoFormatado = decimalFormat.format(resumoUsuario);

                textoSaudacao.setText("Ol??, " + usuario.getNome());
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

        textoSaldo = findViewById(R.id.textSaldo);
        textoSaudacao = findViewById(R.id.textSaudacao);
        nameHeader = findViewById(R.id.nameHeader);

        recyclerView = findViewById(R.id.recyclerMovimentos);
    }

    private void configuraCalendarView() {
        CharSequence meses[] = {"Janeiro", "Fevereiro", "Mar??o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
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
                Intent cp = new Intent(this, ListaProdutoActivity.class);
                startActivity(cp);
                break;
            case R.id.nav_calculadoraTroco:
                Intent ct = new Intent(this, CadastroReceitaActivity.class);
                startActivity(ct);
                break;
            case R.id.nav_historicoVendas:
                Intent hv = new Intent(this, HistoricoDeVendasActivity.class);
                startActivity(hv);
                break;
            case R.id.nav_contasPagar:
                Intent dP = new Intent(this, CadastroDespesasActivity.class);
                startActivity(dP);
                break;
            case R.id.nav_listaClientes:
                Intent lC = new Intent(this, ListaClientesActivity.class);
                startActivity(lC);
                break;
            case R.id.nav_listaFornecedor:
                Intent lF = new Intent(this, ListaFornecedorActivity.class);
                startActivity(lF);
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