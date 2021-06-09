package com.example.herosorveteria.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.herosorveteria.R;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.model.Movimentacao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CalculadoraTrocoActivity extends AppCompatActivity {

    private DatabaseReference registroVendas = FirebaseDatabase.getInstance().getReference();
    EditText valorPago;
    EditText valorCompra;
    TextView viewTroco;
    Button btnCalcularTroco;
    Button btnRegistrarVenda;

    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getDatabaseReference();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private Double receitaTotal;
    private Double receitaGerada;
    private Double receitaAtualizada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora_troco);




        inicializarComponentes();




    }

    public void btnCalcularTroco(View v){
        DatabaseReference vendas = registroVendas.child("vendas");
        String valorP = valorPago.getText().toString();
        double valorPago = Double.parseDouble(valorP);

        String valorC = valorCompra.getText().toString();
        double valorCompra = Double.parseDouble(valorC);

        double valorTroco = valorPago - valorCompra;
        String valorT = Double.toString(valorTroco);

        viewTroco.setText("R$ " + valorT);

        registroVendas.child("Venda 1").setValue("Venda realizada no valor de R$" + valorC + " reais");
        Vendas venda = new Vendas(valorPago, valorCompra, valorTroco, "Dinheiro");

        vendas.child("001").setValue(venda);
    }

    /*public void btnRegistrarVenda(View v){

    }*/

    void inicializarComponentes(){
        valorPago = findViewById(R.id.valorPago);
        valorCompra = findViewById(R.id.valorCompra);
        viewTroco = findViewById(R.id.valorTroco);
        btnCalcularTroco = findViewById(R.id.btnCalcular);
        btnRegistrarVenda = findViewById(R.id.btnRegistrarVenda);


    }
}