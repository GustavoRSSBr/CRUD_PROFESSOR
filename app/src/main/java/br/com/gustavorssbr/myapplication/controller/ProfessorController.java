package br.com.gustavorssbr.myapplication.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.com.gustavorssbr.myapplication.model.Professor;
import br.com.gustavorssbr.myapplication.persistence.ProfessorDao;

public class ProfessorController implements IController<Professor> {

    private final ProfessorDao pDao;

    public ProfessorController(ProfessorDao pDao){
        this.pDao = pDao;
    }

    @Override
    public void inserir(Professor professor) throws SQLException {
        if(pDao.open() == null){
            pDao.open();
        }
        pDao.insert(professor);
        pDao.close();
    }

    @Override
    public void modificar(Professor professor) throws SQLException {
        if(pDao.open() == null){
            pDao.open();
        }

        pDao.update(professor);
        pDao.close();
    }

    @Override
    public void deletar(Professor professor) throws SQLException {
        if(pDao.open() == null){
            pDao.open();
        }

        pDao.delete(professor);
        pDao.close();
    }

    @Override
    public Professor buscar(Professor professor) throws SQLException {
        if(pDao.open() == null){
            pDao.open();
        }

        return pDao.findOne(professor);
    }

    @Override
    public List<Professor> listar() throws SQLException {
        if(pDao.open() == null){
            pDao.open();
        }
        return pDao.findAll();
    }
}
