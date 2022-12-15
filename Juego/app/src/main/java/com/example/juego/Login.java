/*
* Class Login
*
* Author: Jesus Martin Valdivia
*
* CampusFP
*
* In this class it is the same as the record, but instead of saving the data to the database,
 * We ask you to see if you are already registered and if you are, we will go to the next interface
*
*
*
* */

package com.example.juego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText correoLogin, passLogin;
    Button btnEntrar;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //conectar a la vista xml
        correoLogin = findViewById(R.id.correoLogin);
        passLogin = findViewById(R.id.passLogin);
        btnEntrar = findViewById(R.id.btnEntrar);
        auth = FirebaseAuth.getInstance();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = correoLogin.getText().toString();
                String pass = passLogin.getText().toString();

                if(pass.length()<6){
                    passLogin.setError("Contraseña mas de 6 caracteteres");
                }else{
                    LoginJugador(email,pass);
                }
            }
        });
    }

    private void LoginJugador(String email, String pass) {
        auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            startActivity(new Intent(Login.this,Menu.class));
                            assert user != null; //el usuario no es nulo que hay algo
                            Toast.makeText(Login.this, "has entrado panita"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Fallo en algo mi bro", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}