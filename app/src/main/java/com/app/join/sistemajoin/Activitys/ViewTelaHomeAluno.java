package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.join.sistemajoin.R;

public class ViewTelaHomeAluno extends AppCompatActivity {

    private Intent intent;
    private Button btSairPai,btVisualizarPost,btVisualizarAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_aluno);

        btVisualizarPost=(Button)findViewById(R.id.btVisualizarPost);
        btVisualizarAvaliacao=(Button)findViewById(R.id.btVisualizarAvaliacao);
        btSairPai=(Button)findViewById(R.id.btSairPai);
        intent = getIntent();

        btVisualizarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verPost = new Intent(ViewTelaHomeAluno.this, ViewSelecionarFilho.class);
                verPost.putExtra("cpfRes", intent.getStringExtra("cpfRes"));
                verPost.putExtra("post", "post");
                startActivity(verPost);
            }
        });

        btVisualizarAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verPost = new Intent(ViewTelaHomeAluno.this, ViewSelecionarFilho.class);
                verPost.putExtra("cpfRes", intent.getStringExtra("cpfRes"));
                verPost.putExtra("post", "graph");
                startActivity(verPost);
            }
        });

        btSairPai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(ViewTelaHomeAluno.this, ViewTelaLogin.class);
                startActivity(logout);
            }
        });


    }
}
