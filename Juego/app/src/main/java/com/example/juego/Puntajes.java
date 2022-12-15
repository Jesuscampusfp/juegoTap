/*
* Class Puntuajes
*
* Author: Jesus Martin Valdivia
*
* CampusFp
*
* What we have in this class is a list of users, the connection with firebase the adapter
 * from the other class and LinearLayout to order the values
*
*
*
*
* */

package com.example.juego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Puntajes extends AppCompatActivity {
    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    Adaptador adaptador;
    List<Usuario> usuarioList;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Puntuajes:");

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mLayoutManager = new LinearLayoutManager(this);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView);

        /*Que se orden en modo ascendente de a-z*/
        mLayoutManager.setReverseLayout(true);
        /*Desde el primero hasta el ultimo*/
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);/*Para cuando se muestre se pnga todo*/
        usuarioList = new ArrayList<>();

        ObtenerUsuarios();

    }
    private void ObtenerUsuarios(){

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();/*Usuario que esta dentro*/
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Mis datos de los usuarios");
/*Cuando haya un cambio que se actualize como el evento de escucha de js en un boton*/
        ref.orderByChild("Goles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioList.clear();
                /*con un bucle vamos sacando los hijos de la base de datos*/
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Usuario usuario = ds.getValue(Usuario.class);

                    usuarioList.add(usuario);
                    adaptador = new Adaptador(Puntajes.this, usuarioList);
                    recyclerView.setAdapter(adaptador);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}