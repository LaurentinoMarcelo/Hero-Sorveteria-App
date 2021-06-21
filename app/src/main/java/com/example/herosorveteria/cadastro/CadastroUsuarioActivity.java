package com.example.herosorveteria.cadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.herosorveteria.activity.MenuActivity;
import com.example.herosorveteria.R;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.helper.Base64Custom;
import com.example.herosorveteria.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import org.jetbrains.annotations.NotNull;

public class CadastroUsuarioActivity extends AppCompatActivity {

   private EditText campoNome, campoEmail, campoSenha;
   private Button botaoCadastrar;
   private FirebaseAuth autenticacao;
   private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        inicializarComponetes();

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomecadastro = campoNome.getText().toString();
                String emailcadastro = campoEmail.getText().toString();
                String senhaCadastro = campoSenha.getText().toString();

                //Validar campos
                if(!nomecadastro.isEmpty()){
                    if(!emailcadastro.isEmpty()){
                        if(!senhaCadastro.isEmpty()){

                            usuario = new Usuario();
                            usuario.setNome(nomecadastro);
                            usuario.setEmail(emailcadastro);
                            usuario.setSenha(senhaCadastro);
                            cadastrarUsuario();


                        }else {
                            Toast.makeText(CadastroUsuarioActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CadastroUsuarioActivity.this,
                                "Preencha o e-mail!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CadastroUsuarioActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }


                }
        });


    }



    public void inicializarComponetes(){
        campoNome = findViewById(R.id.nomeCadastro);
        campoEmail = findViewById(R.id.emailCadastro);
        campoSenha = findViewById(R.id.senhaCadastro);
        botaoCadastrar = findViewById(R.id.btnCadastro);
    }

    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if( task.isSuccessful() ){

                    String idUsuario = Base64Custom.codificarBase64( usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();
                    finish();

                }else {
                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, digite um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Este e-mail ja esta sendo utilizado";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuarioActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}