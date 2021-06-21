package com.example.herosorveteria.cadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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

public class CadastroDespesasActivity extends AppCompatActivity {

    TextInputEditText campoData, campoValor, campoDescricao;
    Spinner spinnerFormaPagamento, spinnerCategoriaDespesa;
    Button btnRegistrar;
    private String[] categoriaDespesa = {"Fornecedores", "Despesas Fixas", "Despesas Variáveis"};
    private String categoriaDespesaSelecionada;
    private String[] formaPagamento = {"Dinheiro", "Cartão"};
    private String formaPagamentoSelecionado;
    private Movimentacao movimentacaoDespesas;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private Double despesaTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_despesas);

        inicializarComponentes();
        configurarAdapter();
        selecionarAdapter();

        campoData.setText(DateCustom.dataAtual());

        recuperarDespesaTotal();

    }

    private void selecionarAdapter() {
        spinnerCategoriaDespesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        categoriaDespesaSelecionada = "Fornecedor";
                        break;
                    case 1:
                        categoriaDespesaSelecionada = "Despesas Fixas";
                        break;
                    case 2:
                        categoriaDespesaSelecionada = "Despesas Variáveis";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerFormaPagamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        formaPagamentoSelecionado = "Dinheiro";
                        break;
                    case 1:
                        formaPagamentoSelecionado = "Cartão";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void configurarAdapter() {
        ArrayAdapter<String> adapterFormaPagamento = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoriaDespesa);
        spinnerFormaPagamento.setAdapter(adapterFormaPagamento);

        ArrayAdapter<String> adapterCategoriaDespesa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, formaPagamento);
        spinnerCategoriaDespesa.setAdapter(adapterCategoriaDespesa);
    }

    private void inicializarComponentes() {
        campoData = findViewById(R.id.editaDataDespesa);
        campoValor = findViewById(R.id.editValorDespesa);
        campoDescricao = findViewById(R.id.editDescricaoDespesa);
        spinnerFormaPagamento = findViewById(R.id.spinnerFormaPagamentoDespesa);
        spinnerCategoriaDespesa = findViewById(R.id.spinnerCategoriaDespesa);


    }

    public void salvarDespesas(View v){
        if(validarCamposDespesas()) {

            movimentacaoDespesas = new Movimentacao();
            String dataDespesa = campoData.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());

            movimentacaoDespesas.setValor(valorRecuperado);
            movimentacaoDespesas.setCategoria(categoriaDespesaSelecionada);
            movimentacaoDespesas.setDescricao(campoDescricao.getText().toString());
            movimentacaoDespesas.setFormaPagamento(formaPagamentoSelecionado);
            movimentacaoDespesas.setData(campoData.getText().toString());
            movimentacaoDespesas.setTipo("d");

            Double despesaAtualizada = despesaTotal + valorRecuperado;
            atualizarDespesa(despesaAtualizada);

            movimentacaoDespesas.salvar(dataDespesa);

            finish();
        }

    }

    public Boolean validarCamposDespesas(){

        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = categoriaDespesaSelecionada;
        String textoDescricao = campoDescricao.getText().toString();

        if( !textoValor.isEmpty()){
            if( !textoData.isEmpty()){
                if( !textoCategoria.isEmpty()){
                    if( !textoDescricao.isEmpty()){
                        return true;
                    }else {

                        Toast.makeText(CadastroDespesasActivity.this,
                                "Preencha o campo Descrição!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }else {

                    Toast.makeText(CadastroDespesasActivity.this,
                            "Preencha o campo Categoria!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

            }else {

                Toast.makeText(CadastroDespesasActivity.this,
                        "Preencha o campo Data!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

        }else {

            Toast.makeText(CadastroDespesasActivity.this,
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
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void atualizarDespesa(Double despesaAtualizada){
        String emialUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emialUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("despesaTotal").setValue(despesaAtualizada);
    }




}