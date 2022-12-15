/*
* Class register
*
* Author:Jesus Martin Valdivia
*
* CampusFP
*
 * In this class what we have is a record with view variables,
 * We initialize those variables and make an instance with the database,
 * then the registration fields are email and password but also
 * have the name and a date (today's) keep in mind that firebase in the passwords
 * they cannot be less than 6, which would mean several failures, so we tell you that it has to be greater
 * Then we get the values of the inputs and we will put them in the database with a
 * HasMap we will ask for the instance of the database and its name to save the data.
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Register extends AppCompatActivity {

    /*Declarar las variables*/
    EditText correoEt,passEt,nombreEt;
    TextView fechaTxt;
    Button Register;

    //Firebase para la gestion de los usuarios
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Inicializar las variables
        correoEt = findViewById(R.id.correoEt);
        passEt = findViewById(R.id.passEt);
        nombreEt = findViewById(R.id.nombreEt);
        fechaTxt = findViewById(R.id.fechaTxt);
        Register = findViewById(R.id.Registrar);

        auth = FirebaseAuth.getInstance();

        Date date = new Date();                              //formato de fecha
                                                             // 03 de diciembre del 2022
        SimpleDateFormat fecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy");
        String Fecha = fecha.format(date);
        fechaTxt.setText(Fecha);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = correoEt.getText().toString();
                String password = passEt.getText().toString();

                /*Esto se hace porque firebase necesita una pass mayor de 6*/
                if(password.length()<6){
                    passEt.setError("Contraseña mas de 6 caracteteres");
                }else{
                    RegistrarJugador(email,password);
                }


            }
        });



    }


    /*Creamos un metodo para registrar el usuario*/
    private void RegistrarJugador(String email, String password) {

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        /*el usuario es registrado bien*/
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            int contador = 0;

                            assert  user != null;


                            /*Los valores de los inputs de registro*/
                            String serial = user.getUid();
                            String correoS = correoEt.getText().toString();
                            String passS = passEt.getText().toString();
                            String nombreS = nombreEt.getText().toString();
                            String fechaS = fechaTxt.getText().toString();

                            /*Poder enviar los datos a firebase*/
                            HashMap<Object, Object> DatosJugador = new HashMap<>();

                            DatosJugador.put("uid",serial);
                            DatosJugador.put("Email",correoS);
                            DatosJugador.put("Pass",passS);
                            DatosJugador.put("Nombre",nombreS);
                            DatosJugador.put("Fecha",fechaS);
                            DatosJugador.put("Goles",contador);


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Mis datos de los usuarios");
                            reference.child(serial).setValue(DatosJugador);
                            //Tendremos que ir mejor a Login y cargar el usuario no pasar directaembte al menu --Cambiar eso
                            startActivity(new Intent(Register.this,Menu.class));

                            Toast.makeText(Register.this, "Usuario registrado todo ok", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(Register.this, "Algo has tocado comprueba q sea esa base de datos en fire anda mi bro", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                //Si se falla
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this,""+e.getMessage(), Toast.LENGTH_SHORT);
                    }
                });
    }
}