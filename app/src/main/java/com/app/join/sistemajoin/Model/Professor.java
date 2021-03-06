package com.app.join.sistemajoin.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Professor extends Pessoa {

    private String idProfessor;
    private String email;
    private String cpf;
    private String idEscola;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapProfessor = new HashMap<>();
        hashMapProfessor.put("idProfessor", getIdProfessor());
        hashMapProfessor.put("email", getEmail());
        hashMapProfessor.put("cpf", getCpf());
        hashMapProfessor.put("nome", getNome());
        hashMapProfessor.put("telefone", getTelefone());
        hashMapProfessor.put("keyTurma", getKeyTurma());
        hashMapProfessor.put("senha", getSenha());
        hashMapProfessor.put("idEscola", getIdEscola());
        return hashMapProfessor;
    }

    public String getIdEscola() {
        return idEscola;
    }

    public void setIdEscola(String idEscola) {
        this.idEscola = idEscola;
    }

    public String getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
