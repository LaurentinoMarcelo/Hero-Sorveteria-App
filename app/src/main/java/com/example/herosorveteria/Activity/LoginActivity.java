package com.example.herosorveteria.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.herosorveteria.R;
import com.example.herosorveteria.config.ConfiguracaoFireBase;
import com.example.herosorveteria.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    //private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private EditText emailLogin;
    private EditText senhaLogin;
    private Button btnEntrar;
    private Button btnCadastrarConta;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();

      btnEntrar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            String emailLoginCampo = emailLogin.getText().toString();
            String senhaLoginCampo = senhaLogin.getText().toString();

              if(!emailLoginCampo.isEmpty()){
                  if(!senhaLoginCampo.isEmpty()){

                        usuario = new Usuario();
                        usuario.setEmail(emailLoginCampo);
                        usuario.setSenha(senhaLoginCampo);
                        validarLogin();

                      }else {
                          Toast.makeText(LoginActivity.this,
                                  "Preencha a senha!",
                                  Toast.LENGTH_SHORT).show();
                      }
                  }else {
                      Toast.makeText(LoginActivity.this,
                              "Preencha o e-mail!",
                              Toast.LENGTH_SHORT).show();
                  }
              }


      });


    }

    void inicializarComponentes() {

        emailLogin = findViewById(R.id.emailLogin);
        senhaLogin = findViewById(R.id.senhaLogin);
        btnEntrar = findViewById(R.id.botaoLogin);
        btnCadastrarConta = findViewById(R.id.criarConta);

    }

    public void validarLogin(){

        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){
                    Toast.makeText(LoginActivity.this,
                            "Sucesso ao fazer o login",
                            Toast.LENGTH_SHORT).show();
                    abrirTelaPrincipal();

                }else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuário não está cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaPrincipal(){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }

    public void btnCadastrar(View v){
        Intent i = new Intent(this, CadastroUsuario.class);
        startActivity(i);
        finish();
    }
}