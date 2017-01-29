package br.ufscar.mds.gerenciador;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.ufscar.mds.gerenciador.data.Atividade;
import br.ufscar.mds.gerenciador.data.Curso;
import br.ufscar.mds.gerenciador.data.Semestre;
import br.ufscar.mds.gerenciador.database.DbInterface;
import br.ufscar.mds.gerenciador.utils.ListViewAtividadesAdapter;

/**
 * Created by gabri on 29/01/2017.
 */

public class CursoActivity extends Activity {

    Curso course;
    TextView mTextViewTitulo;
    TextView mTextViewHorarios;
    TextView mTextViewFaltas;
    ListView mListViewAtividades;

    Button mButtonIncreaseAbsence;
    Button mButtonDecreaseAbsence;
    Button mButtonClassNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);

        Bundle bundle = getIntent().getExtras();
        int courseId = bundle.getInt("courseId");

        this.course = DbInterface.getCourse(getApplicationContext(), courseId);

        mTextViewTitulo = (TextView) findViewById(R.id.activity_curso_titulo);
        mTextViewHorarios = (TextView) findViewById(R.id.activity_curso_horarios);
        mTextViewFaltas = (TextView) findViewById(R.id.activity_curso_faltas);
        mListViewAtividades = (ListView) findViewById(R.id.activity_curso_listview_atividades);

        mButtonClassNotes = (Button) findViewById(R.id.activity_curso_button_notas);
        mButtonIncreaseAbsence = (Button) findViewById(R.id.activity_curso_button_falta_add);
        mButtonDecreaseAbsence = (Button) findViewById(R.id.activity_curso_button_falta_dec);

        mButtonIncreaseAbsence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course.increaseAbsences();
                DbInterface.updateCourse(getApplicationContext(), course);
                mTextViewFaltas.setText("Número de faltas: " + course.getAbsences());
            }
        });

        mButtonDecreaseAbsence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course.decreaseAbsences();
                DbInterface.updateCourse(getApplicationContext(), course);
                mTextViewFaltas.setText("Número de faltas: " + course.getAbsences());
            }
        });

        mTextViewTitulo.setText(this.course.getNome());
        mTextViewHorarios.setText(this.course.getHorario1() + "\n" + this.course.getHorario2());
        mTextViewFaltas.setText("Número de faltas: " + this.course.getAbsences());

        List<Atividade> atividades = DbInterface.getAllFutureAssignmentsByCourse(getApplicationContext(), courseId);
        mListViewAtividades.setAdapter(new ListViewAtividadesAdapter(getApplicationContext(), atividades));
        mListViewAtividades.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int myItemInt, long l) {
                //TODO Criar visualização da atividade e chamar ela
                Log.v("AtividadesFragment","Selecionado o item: " + myItemInt);
            }
        });


    }
}
