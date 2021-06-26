package com.example.herosorveteria.cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.herosorveteria.R;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.model.Clientes;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class CadastroClienteActivity extends AppCompatActivity {

    TextInputEditText campoNome, campoTelefone, campoRedeSocial;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private Clientes cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        inicializarComponentes();

    }

    public void salvarCliente(View v){
        if(validarCamposClientes()){

            cliente = new Clientes();
            cliente.setNome(campoNome.getText().toString());
            cliente.setTelefone(campoTelefone.getText().toString());
            cliente.setRedeSocial(campoRedeSocial.getText().toString());
            cliente.salvar();
            finish();
        }

    }

    private boolean validarCamposClientes() {
        String textoNome = campoNome.getText().toString();
        String textoTelefone = campoTelefone.getText().toString();
        String textoRedeSocial = campoRedeSocial.getText().toString();

        if(!textoNome.isEmpty()){
            if (!textoTelefone.isEmpty()){
                if (!textoRedeSocial.isEmpty()){
                    return true;
                }else {
                    Toast.makeText(CadastroClienteActivity.this,
                            "Preencha o campo Rede Social!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else {
                Toast.makeText(CadastroClienteActivity.this,
                        "Preencha o campo Telefone!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(CadastroClienteActivity.this,
                    "Preencha o campo Nome!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void inicializarComponentes() {
        campoNome = findViewById(R.id.cadastroNomeCliente);
        campoTelefone = findViewById(R.id.cadastroTelefoneCliente);
        campoRedeSocial = findViewById(R.id.cadastroRedeSocialCliente);
    }
}