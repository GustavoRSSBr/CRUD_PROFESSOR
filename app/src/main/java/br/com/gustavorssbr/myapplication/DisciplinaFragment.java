package br.com.gustavorssbr.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.com.gustavorssbr.myapplication.controller.DisciplinaController;
import br.com.gustavorssbr.myapplication.controller.ProfessorController;
import br.com.gustavorssbr.myapplication.model.Disciplina;
import br.com.gustavorssbr.myapplication.model.Professor;
import br.com.gustavorssbr.myapplication.persistence.DisciplinaDao;
import br.com.gustavorssbr.myapplication.persistence.ProfessorDao;

public class DisciplinaFragment extends Fragment {

    private View view;

    private EditText etCodDisc, etNomeDisc;

    private Button btnBuscarDisc, btnInserirDisc, btnModificarDisc, btnListarDisc, btnExcluirDisc;

    private Spinner spProfDisc;

    private TextView tvListDisc;

    private List<Professor> professores;
    private DisciplinaController disciplinaController;
    private ProfessorController professorController;

    public DisciplinaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_disciplina, container, false);

        etCodDisc = view.findViewById(R.id.etCodDisc);
        etNomeDisc = view.findViewById(R.id.etNomeDisc);

        btnBuscarDisc = view.findViewById(R.id.btnBuscarDisc);
        btnInserirDisc = view.findViewById(R.id.btnInserirDisc);
        btnModificarDisc = view.findViewById(R.id.bntModificarDisc);
        btnListarDisc = view.findViewById(R.id.btnListarDisc);
        btnExcluirDisc = view.findViewById(R.id.btnExcluirDisc);

        spProfDisc = view.findViewById(R.id.spProfDisc);
        tvListDisc = view.findViewById(R.id.tvListDisc);

        tvListDisc.setMovementMethod(new ScrollingMovementMethod());

        disciplinaController = new DisciplinaController(new DisciplinaDao(view.getContext()));
        professorController = new ProfessorController(new ProfessorDao(view.getContext()));

        btnInserirDisc.setOnClickListener(op -> acaoInserir());
        btnModificarDisc.setOnClickListener(op -> acaoModificar());
        btnExcluirDisc.setOnClickListener(op -> acaoExcluir());
        btnBuscarDisc.setOnClickListener(op -> acaoBuscar());
        btnListarDisc.setOnClickListener(op -> acaoListar());

        preencherSpinner();


        return view;
    }

    private void acaoInserir() {
        int selectedItemPosition = spProfDisc.getSelectedItemPosition();

        if(selectedItemPosition > 0){
            Disciplina disciplina = montaDisciplina();

            try {
                disciplinaController.inserir(disciplina);
                Toast.makeText(view.getContext(), "Disciplina Inserida com Sucesso!", Toast.LENGTH_LONG).show();


            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            limpaCampos();

        } else {
            Toast.makeText(view.getContext(), "Selecione um Professor", Toast.LENGTH_LONG).show();
        }
    }

    private void acaoModificar() {
        int selectedItemPosition = spProfDisc.getSelectedItemPosition();

        if(selectedItemPosition > 0){
            Disciplina disciplina = montaDisciplina();

            try {
                disciplinaController.modificar(disciplina);
                Toast.makeText(view.getContext(), "Disciplina Atualizada com Sucesso!", Toast.LENGTH_LONG).show();


            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            limpaCampos();

        } else {
            Toast.makeText(view.getContext(), "Selecione um Professor", Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
            Disciplina d = montaDisciplina();
            try {
                disciplinaController.deletar(d);
                Toast.makeText(view.getContext(), "Disciplina Excluida com Sucesso!", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            limpaCampos();

    }

    private void acaoBuscar() {
        Disciplina d = montaDisciplina();
        try {
            professores = professorController.listar();
            d = disciplinaController.buscar(d);
            if(d.getNome() != null){
                preencheCampos(d);
            }else{
                Toast.makeText(view.getContext(), "Disciplina Não Encontrada!", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void acaoListar() {
        try {
            List<Disciplina> disciplinas = disciplinaController.listar();
            StringBuffer buffer = new StringBuffer();

            disciplinas.forEach(d -> buffer.append(d.toString()).append("\n"));

            tvListDisc.setText(buffer.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void preencherSpinner() {
        Professor p0 = new Professor();
        p0.setCodigo(0);
        p0.setNome("Selecione um professor");
        p0.setTitulacao("");

        try {
            professores = professorController.listar();
            professores.add(0, p0);

            ArrayAdapter ad = new ArrayAdapter(view.getContext(),
                    android.R.layout.simple_spinner_item,
                    professores
            );

            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spProfDisc.setAdapter(ad);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Disciplina montaDisciplina(){
        Disciplina d = new Disciplina();
        d.setCodigo(Integer.parseInt(etCodDisc.getText().toString()));
        d.setNome(etNomeDisc.getText().toString());
        d.setProfessor((Professor) spProfDisc.getSelectedItem());

        return d;
    }

    private void limpaCampos(){
        etCodDisc.setText("");
        etNomeDisc.setText("");
        spProfDisc.setSelection(0);
    }

    public void preencheCampos(Disciplina d){
        etCodDisc.setText(String.valueOf(d.getCodigo()));
        etNomeDisc.setText(d.getNome());

        int cont = 1;
        for(Professor p : professores){
            if(p.getCodigo() == d.getProfessor().getCodigo()){
                spProfDisc.setSelection(cont);
            }else{
                cont++;
            }
        }

        if(cont > professores.size()) {
            spProfDisc.setSelection(0);
        }

    }
}