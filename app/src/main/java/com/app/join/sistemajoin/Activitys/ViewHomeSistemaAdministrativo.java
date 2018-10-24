package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.join.sistemajoin.R;

public class ViewHomeSistemaAdministrativo extends AppCompatActivity {

    Button cadEscola, listaEscola, btSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_sistema_administrativo);

        cadEscola = findViewById(R.id.btCadEscola);
        listaEscola = findViewById(R.id.btListaEscola);


        cadEscola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), ViewCadastrarEscola.class);
                startActivity(in);
            }
        });

        listaEscola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), ViewListarEscolas.class);
                startActivity(in);
            }
        });


    }
}
