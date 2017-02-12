package br.ufscar.mds.gerenciador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.ufscar.mds.gerenciador.data.Atividade;
import br.ufscar.mds.gerenciador.data.Curso;
import br.ufscar.mds.gerenciador.database.DbInterface;
import br.ufscar.mds.gerenciador.utils.ListViewAtividadesAdapter;

/**
 * Created by gabri on 29/01/2017.
 */

public class AtividadeActivity extends Activity {

    TextView mTextViewNome;
    TextView mTextViewDescricao;
    TextView mTextViewCurso;
    TextView mTextViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        Bundle bundle = getIntent().getExtras();
        int assignmentId = bundle.getInt("assignmentId");
        Atividade atividade = DbInterface.getAssignment(getApplicationContext(), assignmentId);
        Curso curso = DbInterface.getCourse(getApplicationContext(), atividade.getCursoId());

        mTextViewNome      = (TextView) findViewById(R.id.activity_atividade_nome);
        mTextViewDescricao = (TextView) findViewById(R.id.activity_atividade_descricao);
        mTextViewCurso     = (TextView) findViewById(R.id.activity_atividade_curso);
        mTextViewData      = (TextView) findViewById(R.id.activity_atividade_data);

        mTextViewNome.setText(atividade.getTitulo());
        mTextViewDescricao.setText(atividade.getDetalhes());
        mTextViewCurso.setText(curso.getNome());
        mTextViewData.setText(atividade.getDataString());
    }
}
