/*
* Class MainActivity
*
* Author: Jesus Martin Valdivia
*
* CampusFP
*
* In this class it is for the user to decide between registering if he does not have an account or logging in
 * if you already have an account created
*
*
* */

package com.example.juego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaramos las variables de los botones
    Button btnLogin,btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conexion a la vista(para que funcione)

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        //Funcionalidad a los botones


        //Nos vamos para el Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast mensaje si se cumple
                //Toast.makeText(MainActivity.this, "has hecho clic en el boyton de login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);

            }
        });
        //Nos vamos para el registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast mensaje si se cumple
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });

    }
}