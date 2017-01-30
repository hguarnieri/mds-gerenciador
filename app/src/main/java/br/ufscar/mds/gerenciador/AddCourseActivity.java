package br.ufscar.mds.gerenciador;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.ufscar.mds.gerenciador.data.Curso;
import br.ufscar.mds.gerenciador.data.Semestre;
import br.ufscar.mds.gerenciador.database.DbInterface;

/**
 * Created by gabri on 29/01/2017.
 */

public class AddCourseActivity extends Activity {
    private EditText mCourseName;
    private EditText mCourseSchedule1;
    private EditText mCourseSchedule2;
    private Spinner mCourseSemesterId;
    private Button mCourseAdd;
    List<Semestre> semesters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        semesters = DbInterface.getAllSemesters(getApplicationContext());
        mCourseName = (EditText) findViewById(R.id.et_course_name);
        mCourseSchedule1 = (EditText) findViewById(R.id.et_course_schedule1);
        mCourseSchedule2 = (EditText) findViewById(R.id.et_course_schedule2);
        renderSpinner();
        mCourseAdd = (Button) findViewById(R.id.bt_course_add);
        mCourseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCourseName.getText() == null){
                    mCourseName.setBackgroundColor(Color.RED);
                }else if(mCourseSchedule1.getText() == null){
                    mCourseSchedule1.setBackgroundColor(Color.RED);
                }else if(mCourseSchedule2.getText() == null){
                    mCourseSchedule2.setBackgroundColor(Color.RED);
                }else if(mCourseSemesterId.getSelectedItem() == null){
                    mCourseSemesterId.setBackgroundColor(Color.RED);
                }else{
                    Curso course = new Curso();
                    course.setNome(mCourseName.getText().toString());
                    course.setHorario1(mCourseSchedule1.getText().toString());
                    course.setHorario2(mCourseSchedule2.getText().toString());
                    int semestreId = mCourseSemesterId.getSelectedItemPosition();
                    course.setSemestreId(semesters.get(semestreId).getId());
                    course.setId(0);
                    DbInterface.saveCourse(getApplicationContext(),course);
                    onBackPressed();
                }
            }
        });
    }

    void renderSpinner(){
        mCourseSemesterId = (Spinner) findViewById(R.id.sp_course_semesterid);

        List<String> foos = new ArrayList<String>();
        for (Semestre obj : semesters) {
            foos.add(obj.getNome());
        }
        ArrayAdapter spinnerArrayAdpter =  new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, foos);
        mCourseSemesterId.setAdapter(spinnerArrayAdpter);
    }
}
