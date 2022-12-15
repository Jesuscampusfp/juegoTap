/*
* Class PantallaCarga
*
* Author: Jesus Martin Valdivia
*
* CampusFP
*
* In this class, which is very simple, what we tell you is that you have a loading time
 * all in seconds, in order to hander run the linesa
 * in the manifest we put this class to be displayed first and then direct us to the MainActivity
*
* */

package com.example.juego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PantallaCarga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_carga);


/*Definir en cuanto tiempo queremos q se ejcute la pantalla de carga*/

        int duracion_carga = 3500; //en segundos

        /*Objeto handler nos hace ejecutar las lineas de codigo en un tiempo determinado*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Intent es el codigo que se va ejecutar en una cierta cantidad de segundos*/
                                                        //Donde se ejetuta(la clase) despues a donde vamos
                Intent intent = new Intent(PantallaCarga.this,MainActivity.class);
                //Que se ejecute el intent
                startActivity(intent);

            };
        }, duracion_carga);



    }
}