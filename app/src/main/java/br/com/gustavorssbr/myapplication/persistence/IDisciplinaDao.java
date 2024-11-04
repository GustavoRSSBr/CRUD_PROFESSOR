package br.com.gustavorssbr.myapplication.persistence;

public interface IDisciplinaDao {
    public DisciplinaDao open();
    public void close();
}
