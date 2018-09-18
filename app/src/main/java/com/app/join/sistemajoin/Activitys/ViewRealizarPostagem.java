package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.join.sistemajoin.Model.Agenda;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;

public class ViewRealizarPostagem extends AppCompatActivity {

    EditText ctTituloPost, ctMsgPost;
    Button btEnviarPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_realizar_postagem);

        ctTituloPost = (EditText) findViewById(R.id.ctTituloPost);
        ctMsgPost = (EditText) findViewById(R.id.ctMsgPost);
        btEnviarPost = (Button) findViewById(R.id.btEnviarPost);


        btEnviarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctTituloPost.equals("") && ctMsgPost.equals("")) {
                    Toast.makeText(ViewRealizarPostagem.this, "Favor Preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else {
                    Agenda post = setDadosAgenda();
                    salvarPost(post);
                    chamaListaPost();
                    finish();
                }
            }
        });

    }

    private void chamaListaPost() {
        Intent listPost = new Intent(ViewRealizarPostagem.this, ViewHomeProfessor.class);
        startActivity(listPost);
    }

    private Agenda setDadosAgenda() {
        Intent post = getIntent();
        Agenda agenda = new Agenda();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        agenda.setData(dateString);
        agenda.setTitulo(ctTituloPost.getText().toString());
        agenda.setIdDestino(post.getStringExtra("key"));
        agenda.setMensagem(ctMsgPost.getText().toString());
        agenda.setNomeProfessor("não codificado");
        return agenda;
    }

    private void salvarPost(Agenda a) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("agenda");
        data.push().setValue(a);
    }
}
