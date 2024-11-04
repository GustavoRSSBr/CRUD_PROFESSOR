package br.com.gustavorssbr.myapplication.model;

import androidx.annotation.NonNull;

public class Professor {

    private int codigo;

    private String nome;

    private String titulacao;

    public Professor(){

    }

    public Professor(int codigo, String nome, String titulacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.titulacao = titulacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }

    @Override
    @NonNull
    public String toString() {
        return  codigo + " - " + titulacao + " - " + nome;
    }
}
