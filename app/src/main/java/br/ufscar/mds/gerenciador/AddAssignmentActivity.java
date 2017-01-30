package br.ufscar.mds.gerenciador;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.ufscar.mds.gerenciador.data.Atividade;
import br.ufscar.mds.gerenciador.data.Curso;
import br.ufscar.mds.gerenciador.data.Semestre;
import br.ufscar.mds.gerenciador.database.DbInterface;
import br.ufscar.mds.gerenciador.utils.CourseArrayAdapter;
import br.ufscar.mds.gerenciador.utils.SemesterArrayAdapter;

/**
 * Created by gabri on 29/01/2017.
 */

public class AddAssignmentActivity extends Activity {
    private EditText mAssignmentTitle;
    private EditText mAssignmentDetalhes;
    private DatePicker mAssignmentDate;
    private TimePicker mAssignmentTime;

    private Spinner mCourseSpinner;
    private Spinner mSemesterSpinner;
    private Button mAddAssignment;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        if (DbInterface.getAllSemesters(getApplicationContext()).size() <= 0) {
            Semestre semestre1 = new Semestre(0, "Semestre 1", 2016, 2);
            Semestre semestre2 = new Semestre(0, "Semestre 2", 2017, 1);

            DbInterface.saveSemester(getApplicationContext(), semestre1);
            DbInterface.saveSemester(getApplicationContext(), semestre2);
        }

        this.mAssignmentTitle = (EditText) findViewById(R.id.activity_add_assignment_titulo);
        this.mAssignmentDetalhes = (EditText) findViewById(R.id.activity_add_assignment_detalhes);
        this.mCourseSpinner = (Spinner) findViewById(R.id.activity_add_assignment_course);
        this.mSemesterSpinner = (Spinner) findViewById(R.id.activity_add_assignment_semester);
        this.mAssignmentDate = (DatePicker) findViewById(R.id.activity_add_assignment_date);
        this.mAssignmentTime = (TimePicker) findViewById(R.id.activity_add_assignment_time);
        this.mAddAssignment = (Button) findViewById(R.id.activity_add_assignment_button_add_assignment);

        renderSpinnerSemesters();

        this.mSemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                renderSpinnerCourses(((Semestre) mSemesterSpinner.getSelectedItem()).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        this.mAddAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAssignmentTitle.getText() == null) {
                    mAssignmentTitle.setBackgroundColor(Color.RED);
                } else if (mAssignmentDetalhes.getText() == null) {
                    mAssignmentDetalhes.setBackgroundColor(Color.RED);
                } else {
                    String time = mAssignmentTime.getCurrentHour() + ":" +mAssignmentTime.getCurrentMinute() + ":00";
                    String date = mAssignmentDate.getYear() + "-" + (mAssignmentDate.getMonth() + 1) + "-"
                                + mAssignmentDate.getDayOfMonth() + " " + time;

                    System.out.println(date);
                    Date assignmentDate = null;
                    try {
                        assignmentDate = sdf.parse(date);

                        Atividade assignment = new Atividade(0, ((Curso) mCourseSpinner.getSelectedItem()).getId(),
                                mAssignmentTitle.getText().toString(), mAssignmentDetalhes.getText().toString(), assignmentDate);
                        long id = DbInterface.saveAssignment(getApplicationContext(), assignment);

                        if (id > 0) {
                            Toast.makeText(getApplicationContext(), "Salvo!", Toast.LENGTH_SHORT);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao salvar atividade", Toast.LENGTH_SHORT);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error on parsing date for new Assignment");
                    }
                }
            }
        });
    }

    void renderSpinnerSemesters(){
        List<Semestre> semesters = DbInterface.getAllSemesters(getApplicationContext());
        ArrayAdapter spinnerArrayAdpter =  new SemesterArrayAdapter(this, semesters);
        mSemesterSpinner.setAdapter(spinnerArrayAdpter);
    }

    void renderSpinnerCourses(int semesterId) {
        List<Curso> courses = DbInterface.getAllCoursesBySemester(getApplicationContext(), semesterId);
        ArrayAdapter spinnerArrayAdpter =  new CourseArrayAdapter(this, courses);
        mCourseSpinner.setAdapter(spinnerArrayAdpter);
    }
}
