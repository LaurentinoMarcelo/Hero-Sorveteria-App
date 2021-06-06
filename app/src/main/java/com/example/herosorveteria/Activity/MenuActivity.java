package com.example.herosorveteria.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.herosorveteria.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);

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
            case R.id.nav_cadastrarProduto:
                Intent cp = new Intent(this, MenuActivity.class);
                startActivity(cp);
                break;
            case R.id.nav_calculadoraTroco:
                Intent ct = new Intent(this, CalculadoraTroco.class);
                startActivity(ct);
                break;
            case R.id.nav_historicoVendas:
                Intent hv = new Intent(this, HistoricoDeVendas.class);
                startActivity(hv);
                break;
            case R.id.nav_contasPagar:
                Intent dP = new Intent(this, Despesas.class);
                startActivity(dP);
                break;
            case R.id.nav_sair:
                autenticacao.signOut();
                Intent lg = new Intent(this, LoginActivity.class);
                startActivity(lg);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}