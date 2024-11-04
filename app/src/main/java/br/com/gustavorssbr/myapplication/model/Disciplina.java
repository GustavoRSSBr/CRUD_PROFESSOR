package br.com.gustavorssbr.myapplication.model;

public class Disciplina {
    private int codigo;
    private String nome;
    private Professor professor;

    public Disciplina(){

    }

    public Disciplina(int codigo, String nome, Professor professor) {
        this.codigo = codigo;
        this.nome = nome;
        this.professor = professor;
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

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return  codigo + " - " + nome + " - " + professor.getNome();
    }
}
