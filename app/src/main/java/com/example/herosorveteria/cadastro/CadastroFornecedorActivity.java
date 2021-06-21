package com.example.herosorveteria.cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.herosorveteria.R;
import com.example.herosorveteria.menu.ListaFornecedorActivity;
import com.example.herosorveteria.model.Fornecedor;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroFornecedorActivity extends AppCompatActivity {

    TextInputEditText nomeFornecedor, telefoneFornecedor, enderecoFornecedor, produtoFornecedor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fornecedor);

        incializarComponentes();


    }
    public void salvarFornecedor(View v){

        validarCampos();
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(nomeFornecedor.getText().toString());
        fornecedor.setTelefone(telefoneFornecedor.getText().toString());
        fornecedor.setEndereço(enderecoFornecedor.getText().toString());
        fornecedor.setProdutoFornecido(produtoFornecedor.getText().toString());
        fornecedor.salvar();

        validarCampos();
        if(validarCampos()==true){
            fornecedor.salvar();
            finish();
        }


    }


    private void incializarComponentes() {
        nomeFornecedor = findViewById(R.id.nomeFornecedorCadastro);
        telefoneFornecedor = findViewById(R.id.telefoneFornecedorCadastro);
        enderecoFornecedor = findViewById(R.id.enderecoFornecedorCadastro);
        produtoFornecedor = findViewById(R.id.produtosFornecedorCadastro);
    }

    public Boolean validarCampos() {

        String nome = nomeFornecedor.getText().toString();
        String telefone = telefoneFornecedor.getText().toString();
        String endereco = enderecoFornecedor.getText().toString();
        String produtos = produtoFornecedor.getText().toString();


        if (!nome.isEmpty()) {
            if (!telefone.isEmpty()) {
                if (!endereco.isEmpty()) {
                    if(!produtos.isEmpty()){
                        return true;
                    }else {
                        Toast.makeText(CadastroFornecedorActivity.this,
                                "Preencha os produto!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } else {
                    Toast.makeText(CadastroFornecedorActivity.this,
                            "Preencha o endereço!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(CadastroFornecedorActivity.this,
                        "Preencha o telefone!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(CadastroFornecedorActivity.this,
                    "Preencha o nome do fornecedor!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

    }


}