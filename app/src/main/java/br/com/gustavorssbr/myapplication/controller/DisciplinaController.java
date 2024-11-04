package br.com.gustavorssbr.myapplication.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.com.gustavorssbr.myapplication.model.Disciplina;
import br.com.gustavorssbr.myapplication.persistence.DisciplinaDao;

public class DisciplinaController implements IController<Disciplina>{
    private final DisciplinaDao dDao;

    public DisciplinaController(DisciplinaDao dDao){
        this.dDao = dDao;
    }

    @Override
    public void inserir(Disciplina disciplina) throws SQLException {
        if(dDao.open() == null){
            dDao.open();
        }

        dDao.insert(disciplina);
        dDao.close();
    }

    @Override
    public void modificar(Disciplina disciplina) throws SQLException {
        if(dDao.open() == null){
            dDao.open();
        }

        dDao.update(disciplina);
        dDao.close();
    }

    @Override
    public void deletar(Disciplina disciplina) throws SQLException {
        if(dDao.open() == null){
            dDao.open();
        }

        dDao.delete(disciplina);
        dDao.close();
    }

    @Override
    public Disciplina buscar(Disciplina disciplina) throws SQLException {
        if(dDao.open() == null){
            dDao.open();
        }

        return dDao.findOne(disciplina);
    }

    @Override
    public List<Disciplina> listar() throws SQLException {
        if(dDao.open() == null){
            dDao.open();
        }
        return dDao.findAll();
    }
}
