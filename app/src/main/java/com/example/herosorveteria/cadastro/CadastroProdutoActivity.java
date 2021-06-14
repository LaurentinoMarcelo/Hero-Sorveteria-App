package com.example.herosorveteria.cadastro;

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
import com.example.herosorveteria.menu.ProdutoActivity;
import com.example.herosorveteria.model.MovimentacaoProdutos;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class CadastroProdutoActivity extends AppCompatActivity {

    TextInputEditText campoNomeProduto, campoValorCompra, campoValorVenda, campoQuantidadeProduto;
    Spinner spinnerCategoriaProduto, spinnerUnidadeProduto;
    TextView valorLucro;
    Button btnCadastrarProduto;
    private String[] categoriaProduto = {"Açaí", "Sorvete", "Picolé", "Insumos Embalagens", "Insumos Escritórios", "Acompanhamento", "Produtos Limpeza"};
    private String[] unidadeProduto = {"Kilo", "Caixa", "Litros", "Pacotes"};
    private String categoriaProdutoSelecionado;
    private String unidadeProdutoSelecionado;
    private MovimentacaoProdutos produto;
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getDatabaseReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        inicializarComponetes();
        configurarAdapter();
        selicionarSpinner();

    }


    public void inicializarComponetes() {
        campoNomeProduto = findViewById(R.id.textNomeProduto);
        campoValorCompra = findViewById(R.id.textValorCompraProduto);
        campoValorVenda = findViewById(R.id.textValorVendaProduto);
        campoQuantidadeProduto = findViewById(R.id.quantiadeProduto);
        spinnerCategoriaProduto = findViewById(R.id.spinnerCategoriaProduto);
        spinnerUnidadeProduto = findViewById(R.id.spinnerUnidadeProduto);
        valorLucro = findViewById(R.id.textLucro);
        btnCadastrarProduto = findViewById(R.id.btnCadastrarProduto);
    }

    private void selicionarSpinner() {
        spinnerCategoriaProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        categoriaProdutoSelecionado = "Açaí";
                        break;
                    case 1:
                        categoriaProdutoSelecionado = "Sorvete";
                        break;
                    case 2:
                        categoriaProdutoSelecionado = "Picolé";
                        break;
                    case 3:
                        categoriaProdutoSelecionado = "Insumos Embalagens";
                        break;
                    case 4:
                        categoriaProdutoSelecionado = "Insumos Escritórios";
                        break;
                    case 5:
                        categoriaProdutoSelecionado = "Acompanhamento";
                        break;
                    case 6:
                        categoriaProdutoSelecionado = "Produtos Limpeza";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        spinnerUnidadeProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 switch (i){
                     case 0:
                         unidadeProdutoSelecionado = "Kilo";
                         break;
                     case 1:
                         unidadeProdutoSelecionado = "Caixa";
                         break;
                     case 2:
                         unidadeProdutoSelecionado = "Litros";
                         break;
                     case 3:
                     unidadeProdutoSelecionado = "Pacotes";
                     break;
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void configurarAdapter() {
        ArrayAdapter<String> adapterCategoriaProduto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoriaProduto);
        spinnerCategoriaProduto.setAdapter(adapterCategoriaProduto);


        ArrayAdapter<String> adapterUnidadeProduto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, unidadeProduto);
        spinnerUnidadeProduto.setAdapter(adapterUnidadeProduto);
    }

    public void salvarProduto(View v) {
        String nomeProduto = campoNomeProduto.getText().toString();
        String valorProduto = campoValorCompra.getText().toString();
        String valorVenda = campoValorVenda.getText().toString();
        String quantidadeProduto = campoQuantidadeProduto.getText().toString();


         produto = new MovimentacaoProdutos();
         produto.setNomeProduto(nomeProduto);
         produto.setValorProduto(valorProduto);
         produto.setCategoriaProduto(categoriaProdutoSelecionado);
         produto.setQuantidadeProduto(quantidadeProduto);
         produto.setUnidadeProduto(unidadeProdutoSelecionado);
         produto.setValorVenda(valorVenda);

        validarCampos();
        if(validarCampos()==true){
            produto.salvar();
            voltarProduto();
        }



    }

    public Boolean validarCampos() {

        String nomeProduto = campoNomeProduto.getText().toString();
        String valorCompra = campoValorCompra.getText().toString();
        String valorVenda = campoValorVenda.getText().toString();
        String qauntidadeProduto = campoQuantidadeProduto.getText().toString();

        if (!nomeProduto.isEmpty()) {
            if (!valorCompra.isEmpty()) {
                if (!valorVenda.isEmpty()) {
                    if(!qauntidadeProduto.isEmpty()){
                        return true;
                    }else {
                        Toast.makeText(CadastroProdutoActivity.this,
                                "Preencha a quantidade produto!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } else {
                    Toast.makeText(CadastroProdutoActivity.this,
                            "Preencha o valor da venda!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(CadastroProdutoActivity.this,
                        "Preencha o valor da compra!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(CadastroProdutoActivity.this,
                    "Preencha o nome produto!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void voltarProduto() {
        Intent i = new Intent(this, ProdutoActivity.class);
        startActivity(i);
        finish();
    }
}

