package br.com.gustavorssbr.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.com.gustavorssbr.myapplication.controller.ProfessorController;
import br.com.gustavorssbr.myapplication.model.Professor;
import br.com.gustavorssbr.myapplication.persistence.ProfessorDao;


public class ProfessorFragment extends Fragment {
    private View view;

    private EditText etCodigoProf, etNomeProf, etTitulacaoProf;

    private Button btnBuscarProf, btnModificarProf, btnInserirProf, btnListarProf, btnExcluirProf;

    private TextView tvListarProf;

    private ProfessorController pCont;


    public ProfessorFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_professor, container, false);
        etCodigoProf = view.findViewById(R.id.etCodigoProf);
        etNomeProf = view.findViewById(R.id.etNomeProf);
        etTitulacaoProf = view.findViewById(R.id.etTitulacaoProf);

        btnBuscarProf = view.findViewById(R.id.btnBuscarProf);
        btnModificarProf = view.findViewById(R.id.btnModificarProf);
        btnInserirProf = view.findViewById(R.id.btnInserirProf);
        btnListarProf = view.findViewById(R.id.btnListarProf);
        btnExcluirProf = view.findViewById(R.id.btnExcluirProf);

        tvListarProf = view.findViewById(R.id.tvListarProf);
        tvListarProf.setMovementMethod(new ScrollingMovementMethod());

        pCont = new ProfessorController(new ProfessorDao(view.getContext()));

        btnInserirProf.setOnClickListener(op -> acaoInserir());
        btnModificarProf.setOnClickListener(op -> acaoModificar());
        btnExcluirProf.setOnClickListener(op -> acaoExcluir());
        btnBuscarProf.setOnClickListener(op -> acaoBuscar());
        btnListarProf.setOnClickListener(op -> acaoListar());


        return  view;
    }

    private void acaoListar() {
        try {
            List<Professor> professores = pCont.listar();
            StringBuffer buffer = new StringBuffer();

            professores.forEach(p -> buffer.append(p.toString()).append("\n"));
            tvListarProf.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoBuscar() {
        Professor professor = montarProfessor();

        try {
           professor = pCont.buscar(professor);

           if(professor.getNome() != null){
               preencheCampos(professor);
           }else{
               Toast.makeText(view.getContext(), "Professor Não Encontrado!", Toast.LENGTH_LONG).show();
               limpaCampos();
           }

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        Professor professor = montarProfessor();

        try {
            pCont.deletar(professor);
            Toast.makeText(view.getContext(), "Professor Deletado com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void acaoModificar() {
        Professor professor = montarProfessor();

        try {
            pCont.modificar(professor);
            Toast.makeText(view.getContext(), "Professor Atualizado com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void acaoInserir() {
        Professor professor = montarProfessor();

        try {
            pCont.inserir(professor);
            Toast.makeText(view.getContext(), "Professor Inserido com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private Professor montarProfessor(){
        Professor p = new Professor();
        p.setCodigo(Integer.parseInt(etCodigoProf.getText().toString()));
        p.setNome(etNomeProf.getText().toString());
        p.setTitulacao(etTitulacaoProf.getText().toString());

        return p;
    }

    private void preencheCampos(Professor p){
        etCodigoProf.setText(String.valueOf(p.getCodigo()));
        etNomeProf.setText(p.getNome());
        etTitulacaoProf.setText(p.getTitulacao());
    }

    private void limpaCampos(){
        etCodigoProf.setText("");
        etNomeProf.setText("");
        etTitulacaoProf.setText("");
    }
}