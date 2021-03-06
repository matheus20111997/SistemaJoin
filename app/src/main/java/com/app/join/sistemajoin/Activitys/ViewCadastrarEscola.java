package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;

import java.util.InputMismatchException;

public class ViewCadastrarEscola extends AppCompatActivity {

    private EditText ctNomeEsc, ctTelEsc, ctEmailEsc, ctCNPJEsc;
    private Button btSalvarEsc;
    private FirebaseAuth autenticacao;
    private Escola escola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_escola);

        ctTelEsc = (EditText) findViewById(R.id.ctTelEsc);
        ctEmailEsc = (EditText) findViewById(R.id.ctEmailEsc);
        ctNomeEsc = (EditText) findViewById(R.id.ctNomeEsc);
        ctCNPJEsc = (EditText) findViewById(R.id.ctCNPJEsc);
        btSalvarEsc = (Button) findViewById(R.id.btSalvarEscola);

        SimpleMaskFormatter simpleMaskTel = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mascaraTel = new MaskTextWatcher(ctTelEsc, simpleMaskTel);
        ctTelEsc.addTextChangedListener(mascaraTel);

        SimpleMaskFormatter simpleMaskCNPJ = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher mascaraCNPJ = new MaskTextWatcher(ctCNPJEsc, simpleMaskCNPJ);
        ctCNPJEsc.addTextChangedListener(mascaraCNPJ);

        final Intent intent = getIntent();
        final String key = intent.getStringExtra("key");

        if (key != null) {
            ctNomeEsc.setText(intent.getStringExtra("nome"));
            ctTelEsc.setText(intent.getStringExtra("tel"));
            ctCNPJEsc.setText(intent.getStringExtra("cnpj"));
            ctEmailEsc.setText(intent.getStringExtra("email"));
            ctCNPJEsc.setEnabled(false);

            btSalvarEsc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ctNomeEsc.getText().length() < 1) {
                        ctNomeEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctTelEsc.getText().length() < 1) {
                        ctTelEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctEmailEsc.getText().length() < 1) {
                        ctEmailEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctCNPJEsc.getText().length() < 1) {
                        ctCNPJEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctTelEsc.getText().length() < 15 && ctTelEsc.getText().length() > 1) {
                        ctTelEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Favor, preencher os campos corretamente!", Toast.LENGTH_SHORT).show();
                    } else {
                        escola = new Escola();
                        escola.setNome(ctNomeEsc.getText().toString());
                        escola.setTelefone(ctTelEsc.getText().toString());
                        escola.setEmail(ctEmailEsc.getText().toString());
                        escola.setCnpj(intent.getStringExtra("cnpj"));
                        escola.setId(key);
                        escola.setSenha(intent.getStringExtra("senha"));
                        if(escola.getEmail().equals(intent.getStringExtra("email"))){
                            editar(escola);
                            chamatelaListaescola();
                            finish();
                        }else {
                            cadastrar();
                            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                            autenticacao.signInWithEmailAndPassword(escola.getEmail(), escola.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        editar(escola);
                                        chamatelaListaescola();
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                }
            });
        } else {
            btSalvarEsc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ctNomeEsc.getText().length() < 1) {
                        ctNomeEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctTelEsc.getText().length() < 1) {
                        ctTelEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctEmailEsc.getText().length() < 1) {
                        ctEmailEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctCNPJEsc.getText().length() < 1) {
                        ctCNPJEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctTelEsc.getText().length() < 15 && ctTelEsc.getText().length() > 1||ctCNPJEsc.getText().length()<18&&ctCNPJEsc.getText().length()>1) {
                        ctTelEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Favor, preencher os campos corretamente!", Toast.LENGTH_SHORT).show();
                    } else if (!validaCnpj(ctCNPJEsc.getText().toString())) {
                        ctCNPJEsc.isSelected();
                        Toast.makeText(getBaseContext(), "Os dados inseridos são inválidos!", Toast.LENGTH_SHORT).show();
                    } else {
                        escola = new Escola();
                        escola.setNome(ctNomeEsc.getText().toString());
                        escola.setTelefone(ctTelEsc.getText().toString());
                        escola.setEmail(ctEmailEsc.getText().toString());
                        escola.setCnpj(ctCNPJEsc.getText().toString());
                        escola.setSenha(geraSenha(ctCNPJEsc.getText().toString()));
                        String idUsuario = Base64Custon.codificadorBase64(escola.getEmail());
                        escola.setId(idUsuario);
                        cadastrar();
                        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                        autenticacao.signInWithEmailAndPassword(escola.getEmail(), escola.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    salvar(escola);
                                    chamatelaListaescola();
                                    finish();
                                }
                            }
                        });

                    }
                }
            });
        }

    }

    private void editar(Escola e) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("escola");
        data.child(e.getId()).updateChildren(escola.toMap());
    }

    public void cadastrar() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(escola.getEmail(), escola.getSenha())
                .addOnCompleteListener(ViewCadastrarEscola.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Preferencias preferencias = new Preferencias(ViewCadastrarEscola.this);
                                    preferencias.salvaUsuarioLogado(escola.getId(), escola.getNome());
                                } else {
                                    String erroExcecao = "";
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        erroExcecao = "E-mail inválido!";
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        erroExcecao = "E-mail já cadastrado!";
                                    } catch (Exception e) {
                                        erroExcecao = "Erro no cadastro!";
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getBaseContext(), erroExcecao, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }

    public String geraSenha(String CNPJ) {
        CNPJ = CNPJ.replace('.', ' ');
        CNPJ = CNPJ.replace('/', ' ');
        CNPJ = CNPJ.replace('-', ' ');
        CNPJ = CNPJ.replaceAll(" ", "");
        String senha = "";
        senha = CNPJ.substring(0, 6);
        return senha;
    }

    public void chamatelaListaescola() {
        Intent i = new Intent(getBaseContext(), ViewListarEscolas.class);
        startActivity(i);
    }

    private void salvar(Escola e) {
        DatabaseReference dataEscola = ConfiguracaoFirebase.getFirebase().child("escola");
        dataEscola.child(e.getId()).setValue(e);

    }

    private boolean validaCnpj(String CNPJ) {

        CNPJ = CNPJ.replace('.', ' ');
        CNPJ = CNPJ.replace('/', ' ');
        CNPJ = CNPJ.replace('-', ' ');
        CNPJ = CNPJ.replaceAll(" ", "");

        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))
            return (false);

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char) ((11 - r) + 48);

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char) ((11 - r) + 48);


            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }


}
