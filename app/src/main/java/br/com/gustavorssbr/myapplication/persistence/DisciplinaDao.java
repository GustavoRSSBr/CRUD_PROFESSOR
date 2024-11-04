package br.com.gustavorssbr.myapplication.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.gustavorssbr.myapplication.model.Disciplina;
import br.com.gustavorssbr.myapplication.model.Professor;

public class DisciplinaDao implements IDisciplinaDao, ICRUDao<Disciplina> {
    private final Context context;
    private GenericDao gdao;
    private SQLiteDatabase database;

    public DisciplinaDao(Context context){
        this.context = context;
    }

    @Override
    public DisciplinaDao open() {
        gdao = new GenericDao(context);
        database = gdao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gdao.close();
    }

    @Override
    public void insert(Disciplina disciplina) throws SQLException {
        ContentValues contentValues = getContentValues(disciplina);
        database.insert("disciplina", null, contentValues);

    }

    @Override
    public int update(Disciplina disciplina) throws SQLException {
        ContentValues contentValues = getContentValues(disciplina);
        return database.update("disciplina", contentValues, "codigo = " +
                disciplina.getCodigo(), null);
    }

    @Override
    public void delete(Disciplina disciplina) throws SQLException {
        database.delete("disciplina", "codigo = " +
                disciplina.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Disciplina findOne(Disciplina disciplina) throws SQLException {
        String sql = "SELECT p.codigo AS cod_prof, p.nome AS nome_prof, p.titulacao AS tit_prof, " +
                "d.codigo, d.nome " +
                "FROM professor p, disciplina d " +
                "WHERE p.codigo = d.codigo_professor " +
                "AND d.codigo = " + disciplina.getCodigo();

        Cursor cursor = database.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        if(!cursor.isAfterLast()){
            Professor professor = new Professor();
            professor.setCodigo(cursor.getInt(cursor.getColumnIndex("cod_prof")));
            professor.setNome(cursor.getString(cursor.getColumnIndex("nome_prof")));
            professor.setTitulacao(cursor.getString(cursor.getColumnIndex("tit_prof")));

            disciplina.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            disciplina.setNome(cursor.getString(cursor.getColumnIndex("nome")));

            disciplina.setProfessor(professor);

        }
        cursor.close();

        return disciplina;
    }

    @SuppressLint("Range")
    @Override
    public List<Disciplina> findAll() throws SQLException {
        List<Disciplina> lista = new ArrayList<>();


        String sql = "SELECT p.codigo AS cod_prof, p.nome AS nome_prof, p.titulacao AS tit_prof, " +
                "d.codigo, d.nome " +
                "FROM professor p, disciplina d " +
                "WHERE p.codigo = d.codigo_professor ";

        Cursor cursor = database.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()){
            Disciplina disciplina = new Disciplina();
            Professor professor = new Professor();
            professor.setCodigo(cursor.getInt(cursor.getColumnIndex("cod_prof")));
            professor.setNome(cursor.getString(cursor.getColumnIndex("nome_prof")));
            professor.setTitulacao(cursor.getString(cursor.getColumnIndex("tit_prof")));

            disciplina.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            disciplina.setNome(cursor.getString(cursor.getColumnIndex("nome")));

            disciplina.setProfessor(professor);

            lista.add(disciplina);
            cursor.moveToNext();

        }

        cursor.close();


        return lista;
    }

    private static ContentValues getContentValues(Disciplina disciplina) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codigo", disciplina.getCodigo());
        contentValues.put("nome", disciplina.getNome());
        contentValues.put("codigo_professor", disciplina.getProfessor().getCodigo());

        return contentValues;
    }


}
