package com.example.herosorveteria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CalculadoraTroco extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    EditText valorPago;
    EditText valorCompra;
    TextView viewTroco;
    Button btnCalcularTroco;
    Button btnRegistrarVenda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora_troco);

        inicializarComponentes();




    }

    public void btnCalcularTroco(View v){

        String valorP = valorPago.getText().toString();
        double valorPago = Double.parseDouble(valorP);

        String valorC = valorCompra.getText().toString();
        double valorCompra = Double.parseDouble(valorC);

        double valorTroco = valorPago - valorCompra;
        String valorT = Double.toString(valorTroco);

        viewTroco.setText("R$ " + valorT);

        reference.child("Venda 1").setValue("Venda realizada no valor de R$" + valorC + " reais");



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