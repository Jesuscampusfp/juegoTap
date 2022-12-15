/*
*
*Class Juego
*
* Author: Jesus Martin valdivia
*
* CampusFP
*
* This is the class in which the user can interact with the game
 *
 * We have a TVduck which is an image and if we click on the counter, it will add up,
 * After another Tvpato with another different image and also the counter will be added in
 * The Screen function what we do is know the size of our device
 * so that it does not disappear from our screens when doing a random and that it is always visible,
 * The movement would be random minus what the TvPato occupies, each time the user plays it will be updated
 * in the database with the SaveResults function
* */

package com.example.juego;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Random;

public class Juego extends AppCompatActivity {
        String NombreS,PatoS;
        TextView TvContador,TvNombre,TvTiempo;
        ImageView TvPato, TvPato2;
        TextView AnchoTv, AltoTv;
        int AnchoPantalla;
        int AltoPantalla;
    Random aleatorio;
        int contador = 0;

        boolean Fin = false;
        Dialog miDialog;

        FirebaseAuth firebaseAuth;
        FirebaseUser user;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference JUGADORES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        TvPato = findViewById(R.id.TvPato);
        TvPato2 = findViewById(R.id.TvPato2);
        TvContador = findViewById(R.id.TvContador);
        TvNombre = findViewById(R.id.TvNombre);
        TvTiempo = findViewById(R.id.TvTiempo);
        AltoTv = findViewById(R.id.AltoTv);
        AnchoTv = findViewById(R.id.AnchoTv);
        Pantalla();
        CuentaAtras();
        miDialog = new Dialog(Juego.this);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        JUGADORES = firebaseDatabase.getReference("Mis datos de los usuarios");



        Bundle intent = getIntent().getExtras();
        NombreS = intent.getString("Nombre");
        PatoS = intent.getString("Patitos");

        TvNombre.setText(NombreS);
        TvContador.setText(PatoS);

        TvPato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Fin) {

                    //contador = contador +1
                    contador++;
                    //lo cambiamos a cadena porque es un int y lo  pasamos a cadena
                    TvContador.setText(String.valueOf(contador));

                    TvPato.setImageResource(R.drawable.dukom);

                    //permite cambia la imagen a sui estado original
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TvPato.setImageResource(R.drawable.elduko);
                            Movimiento();
                        }
                    }, 500);
                }
            }
        });

        TvPato2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Fin) {

                    //contador = contador +1
                    contador++;
                    //lo cambiamos a cadena porque es un int y lo  pasamos a cadena
                    TvContador.setText(String.valueOf(contador));

                    TvPato2.setImageResource(R.drawable.pato1m);

                    //permite cambia la imagen a sui estado original
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TvPato2.setImageResource(R.drawable.pato1);
                            Movimiento2();
                        }
                    }, 500);
                }
            }
        });

    }

    private void Pantalla(){
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        AnchoPantalla = point.x;
        AltoPantalla = point.y;

        String ANCHOS = String.valueOf(AnchoPantalla);
        String ALTOS = String.valueOf(AltoPantalla);
        AnchoTv.setText(ANCHOS);
        AltoTv.setText(ALTOS);
        aleatorio = new Random();
    }


    private  void Movimiento(){
        int min = 0;
        int MaximoX = AnchoPantalla - TvPato .getWidth();
        int MaximoY = AltoPantalla - TvPato.getHeight();


        int randomX = aleatorio.nextInt(((MaximoX - min)+1)+min);
        int randomY = aleatorio.nextInt(((MaximoY - min) +1)+min);

        TvPato.setX(randomX);
        TvPato.setY(randomY);

    }

    private  void Movimiento2(){
        int min = 0;
        int MaximoX = AnchoPantalla - TvPato2 .getWidth();
        int MaximoY = AltoPantalla - TvPato2.getHeight();


        int randomX = aleatorio.nextInt(((MaximoX - min)+1)+min);
        int randomY = aleatorio.nextInt(((MaximoY - min) +1)+min);

        TvPato2.setX(randomX);
        TvPato2.setY(randomY);

    }






    private void CuentaAtras(){
        new CountDownTimer(10000,1000){


            public void onTick(long millisUntilFinished){
                long segundosRestantes = millisUntilFinished/1000;
                TvTiempo.setText(segundosRestantes+"S");

            }

            public void onFinish(){
                TvTiempo.setText("0S");
                Fin =  true;
                MensajeFin();
                GuardarResultados("Goles",contador);

            }
        }.start();
    }

    private void MensajeFin() {
        TextView hasmatado,numero,seacabado;
        Button jugarotravez,irmenu,puntuaje;

        miDialog.setContentView(R.layout.fin);

        seacabado = miDialog.findViewById(R.id.seacabado);
        numero = miDialog.findViewById(R.id.numero);
        hasmatado = miDialog.findViewById(R.id.hasmatado);

        jugarotravez = miDialog.findViewById(R.id.jugarotravez);
        irmenu = miDialog.findViewById(R.id.irmenu);
        puntuaje = miDialog.findViewById(R.id.puntuaje);

        String patos = String.valueOf(contador);
        numero.setText(patos);

        jugarotravez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador = 0;
                miDialog.dismiss();
                TvContador.setText("0");
                Fin = false;
                CuentaAtras();
                Movimiento();
            }
        });
        irmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( Juego.this,Menu.class));
            }
        });

        puntuaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(Juego.this, "Puntuajes", Toast.LENGTH_SHORT).show();
            }
        });

        miDialog.show();
    }

    private void GuardarResultados(String key, int patos){

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(key,patos);
        JUGADORES.child(user.getUid()).updateChildren(hashMap);

    }


}