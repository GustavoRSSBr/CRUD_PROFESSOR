package br.com.gustavorssbr.myapplication.persistence;

import java.sql.SQLException;

import br.com.gustavorssbr.myapplication.model.Professor;

public interface IProfessorDao {
    public ProfessorDao open() throws SQLException;
    public void close();
}
