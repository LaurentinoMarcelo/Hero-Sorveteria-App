package com.example.herosorveteria.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.herosorveteria.Activity.MenuActivity;
import com.example.herosorveteria.R;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.helper.Base64Custom;
import com.example.herosorveteria.helper.DateCustom;
import com.example.herosorveteria.model.Movimentacao;
import com.example.herosorveteria.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class DespesasActivity extends AppCompatActivity {

    TextInputEditText campoData, campoCategoria, campoDescricao, campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getDatabaseReference();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private Double despesaTotal;
    private Double despesaGerada;
    private Double despesaAtualizada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        inicializarComponentes();

        campoData.setText(DateCustom.dataAtual());

        recuperarDespesaTotal();

    }

    private void inicializarComponentes() {
        campoData = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);

    }

    public void salvarDespesas(View v){
        validarCamposDespesas();
        String dataDespesa = campoData.getText().toString();
        Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());

        movimentacao = new Movimentacao();
        movimentacao.setValor(valorRecuperado);
        movimentacao.setCategoria(campoCategoria.getText().toString());
        movimentacao.setDescricao(campoDescricao.getText().toString());
        movimentacao.setData(campoData.getText().toString());
        movimentacao.setTipo("d");

        despesaGerada = valorRecuperado;
        despesaAtualizada = despesaTotal + despesaGerada;

        movimentacao.salvar(dataDespesa);
        atualizarDespesa();
        finish();


    }

    public Boolean validarCamposDespesas(){

        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();

        if( !textoValor.isEmpty()){
            if( !textoData.isEmpty()){
                if( !textoCategoria.isEmpty()){
                    if( !textoDescricao.isEmpty()){
                        return true;
                    }else {

                        Toast.makeText(DespesasActivity.this,
                                "Preencha o campo Descrição!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }else {

                    Toast.makeText(DespesasActivity.this,
                            "Preencha o campo Categoria!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

            }else {

                Toast.makeText(DespesasActivity.this,
                        "Preencha o campo Data!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

        }else {

            Toast.makeText(DespesasActivity.this,
                    "Preencha o campo Valor!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }


    }
    public void recuperarDespesaTotal(){
        String emialUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emialUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesas();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void atualizarDespesa(){
        String emialUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emialUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("despesas").setValue(despesaAtualizada);
    }

    public void abrirTelaPrincipal(){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();

    }


}