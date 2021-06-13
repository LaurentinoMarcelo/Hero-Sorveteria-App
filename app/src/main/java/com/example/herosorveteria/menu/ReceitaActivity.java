package com.example.herosorveteria.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.herosorveteria.activity.MenuActivity;
import com.example.herosorveteria.R;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.helper.Base64Custom;
import com.example.herosorveteria.helper.DateCustom;
import com.example.herosorveteria.model.MovimentacaoReceitas;
import com.example.herosorveteria.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ReceitaActivity extends AppCompatActivity {

    TextInputEditText campoData, campoDescricao, valorPago, valorCompra;
    TextView campoValor;
    Spinner spinnerCategoria, spinnerFormaPagamento;
    Button btnCalucarTroco, btnRegistrarVenda;
    private MovimentacaoReceitas movimentacaoReceitas;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getDatabaseReference();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private Double receitaTotal;
    private Double receitaGerada;
    private Double receitaAtualizada;
    private String[] formaPagamento = {"Dinheiro", "Cartão", "Delivery"};
    private String[] categoria = {"Açaí", "Sorvete", "Picolé", "Churros", "Misturado"};
    private String formaPagamentoSelecionado;
    private String categoriaSelecionado;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        inicializarComponentes();

        campoData.setText(DateCustom.dataAtual());

        recuperarReceitaTotal();

        configurarAdapter();

        selicionarSpinner();

    }

    private void selicionarSpinner() {
        spinnerFormaPagamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
              if(spinnerFormaPagamento.getSelectedItem().equals("Dinheiro")){
                    formaPagamentoSelecionado = "Dinheiro";
                }if(spinnerFormaPagamento.getSelectedItem().equals("Cartão")){
                        formaPagamentoSelecionado = "Cartão";
                    }if(spinnerFormaPagamento.getSelectedItem().equals("Delivery")){
                            formaPagamentoSelecionado = "Delivery";
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        categoriaSelecionado = "Açaí";
                        break;
                    case 1:
                        categoriaSelecionado = "Sorvete";
                        break;
                    case 2:
                        categoriaSelecionado = "Picolé";
                        break;
                    case 3:
                        categoriaSelecionado = "Churros";
                        break;
                    case 4:
                        categoriaSelecionado = "Misturado";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void configurarAdapter() {

        ArrayAdapter<String> adapterFormaPagamento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, formaPagamento);
        spinnerFormaPagamento.setAdapter(adapterFormaPagamento);

        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoria);
        spinnerCategoria.setAdapter(adapterCategoria);
    }

    private void inicializarComponentes() {
        campoData = findViewById(R.id.editData);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerFormaPagamento = findViewById(R.id.spinnerFormaPagamento);
        btnCalucarTroco = findViewById(R.id.buttonCalcular);
        btnRegistrarVenda = findViewById(R.id.buttonRegistar);
        valorPago = findViewById(R.id.editvalorPago);
        valorCompra = findViewById(R.id.editValorCompra);

    }

    public void salvarReceitas(View v){
        validarCamposReceitas();
        String dataReceita = campoData.getText().toString();
        Double valorRecuperado = Double.parseDouble(valorCompra.getText().toString());

        movimentacaoReceitas = new MovimentacaoReceitas();
        movimentacaoReceitas.setValor(valorRecuperado);
        movimentacaoReceitas.setCategoria(categoriaSelecionado);
        movimentacaoReceitas.setDescricao(campoDescricao.getText().toString());
        movimentacaoReceitas.setData(campoData.getText().toString());
        movimentacaoReceitas.setFormaPagamento(formaPagamentoSelecionado);
        movimentacaoReceitas.setTipo("r");

        receitaGerada = valorRecuperado;
        receitaAtualizada = receitaTotal + receitaGerada;

        movimentacaoReceitas.salvar(dataReceita);
        atualizarDespesa();
        finish();
    }
     public void calcularTroco(View v){

         String valorP = valorPago.getText().toString();
         double valorPago = Double.parseDouble(valorP);

         String valorC = valorCompra.getText().toString();
         double valorCompra = Double.parseDouble(valorC);

         double valorTroco = valorPago - valorCompra;
         String formatadoTroco = String.format("O troco é de R$ %.2f", valorTroco);
         campoValor.setText(formatadoTroco);

     }

    public Boolean validarCamposReceitas(){

        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = categoriaSelecionado;
        String textoFormaPagamento = formaPagamentoSelecionado;


        if( !textoValor.isEmpty()){
            if( !textoData.isEmpty()){
                if( !textoCategoria.isEmpty()){
                    if( !textoFormaPagamento.isEmpty()){
                        return true;
                    }else {

                        Toast.makeText(ReceitaActivity.this,
                                "Preencha o campo Descrição!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }else {

                    Toast.makeText(ReceitaActivity.this,
                            "Preencha o campo Categoria!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

            }else {

                Toast.makeText(ReceitaActivity.this,
                        "Preencha o campo Data!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

        }else {

            Toast.makeText(ReceitaActivity.this,
                    "Preencha o campo Valor!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }


    }

    public void recuperarReceitaTotal(){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceita();

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

        usuarioRef.child("receita").setValue(receitaAtualizada);
    }

    public void abrirTelaPrincipal(){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();

    }
}