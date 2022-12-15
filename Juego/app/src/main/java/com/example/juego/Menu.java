/*
* Class Menu
*
* Author: Jesus Martin Valdivia
*
* CampusFP
*
* In this class is the main screen of our Game, in which it collects all
 * the data that we request from the database such as the name, the dead ducklings,
 * the mail, in addition to the buttons for the interactions with the user of Play and Ranking
 * In addition to an asynchronous task to leave with a countdown and close everything
*
*
* */

package com.example.juego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {

    TextView MiPuntuaciontxt,correo,nombre,menutxt,patitos,salir;
    Button jugarBtn,puntuacionesBtn,acercaDeBtn;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference Usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        jugarBtn = findViewById(R.id.JugarBtn);
        puntuacionesBtn = findViewById(R.id.PuntuacionesBtn);
        acercaDeBtn = findViewById(R.id.AcercaDeBtn);

        MiPuntuaciontxt = findViewById(R.id.MiPuntuacion);
        salir = findViewById(R.id.salir);
        correo = findViewById(R.id.correo);
        nombre = findViewById(R.id.nombre);
        menutxt = findViewById(R.id.Menutxt);
        patitos = findViewById(R.id.patitos);


        //base de datos
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        Usuarios = firebaseDatabase.getReference("Mis datos de los usuarios");

        acercaDeBtn.isClickable();
        acercaDeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Menu.this, "Cuenta atrás iniciada", Toast.LENGTH_LONG).show();
                new myAsincrona().execute();

            }
        });



        jugarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,Juego.class);
                String NombreS = nombre.getText().toString();
                String PatosS = patitos.getText().toString();
                intent.putExtra("Nombre",NombreS);
                intent.putExtra("Patitos",PatosS);

                startActivity(intent);
                Toast.makeText(Menu.this, "Enviado",Toast.LENGTH_SHORT).show();



            }
        });

        puntuacionesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Puntajes.class);
                startActivity(intent);
            }
        });



    }

    private class myAsincrona extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... strings) {

            int i = 120;
            while (i >= 0) {
                try {
                    Thread.sleep(500);
                    publishProgress(i);
                    i--;
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }



            finishAndRemoveTask();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (salir != null){
                salir.setText("Saliendo:: " + values[0]);
            }
            else{
                System.out.println("hola");
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            salir.setText(s);
        }
    }


    protected void onStart() {
        Consulta();
        super.onStart();
    }

    private void Consulta(){
        //hacer la peticion/consulta ordenarlos por correo para hacer comparacion con el correo del inicio de sesion
        Query query = Usuarios.orderByChild("Email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //recorrer

                    String patosString = ""+ds.child("Goles").getValue();

                    String emailString = ""+ds.child("Email").getValue();
                    String nombreString = ""+ds.child("Nombre").getValue();

                    patitos.setText(patosString);

                    correo.setText(emailString);
                    nombre.setText(nombreString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





}