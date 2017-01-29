package br.ufscar.mds.gerenciador;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufscar.mds.gerenciador.data.Semestre;
import br.ufscar.mds.gerenciador.database.DbInterface;

/**
 * Created by gabri on 29/01/2017.
 */

public class AddSemesterActivity extends Activity {
    private EditText mSemesterTerm;
    private EditText mSemesterYear;
    private EditText mSemesterName;
    private Button mSemesterAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);
        mSemesterName = (EditText) findViewById(R.id.et_semester_name);
        mSemesterYear = (EditText) findViewById(R.id.et_semester_year);
        mSemesterTerm = (EditText) findViewById(R.id.et_semester_term);
        mSemesterAdd = (Button) findViewById(R.id.bt_semester_add);

        mSemesterAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSemesterName.setBackgroundColor(Color.TRANSPARENT);
                mSemesterYear.setBackgroundColor(Color.TRANSPARENT);
                mSemesterTerm.setBackgroundColor(Color.TRANSPARENT);
                if(mSemesterName.getText().toString().matches("")){
                    mSemesterName.setBackgroundColor(Color.RED);
                }else if(mSemesterYear.getText().toString().matches("")){
                    mSemesterYear.setBackgroundColor(Color.RED);
                }else if(mSemesterTerm.getText().toString().matches("")){
                    mSemesterTerm.setBackgroundColor(Color.RED);
                }else{
                    Log.v("TAG", mSemesterName.getText().toString());
                    Semestre semester = new Semestre();
                    semester.setNome(mSemesterName.getText().toString());
                    try{
                        semester.setAno(Integer.parseInt(mSemesterYear.getText().toString()));
                        semester.setPeriodo(Integer.parseInt(mSemesterTerm.getText().toString()));
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }

                    semester.setId(0);
                    DbInterface.saveSemester(getApplicationContext(),semester);
                    onBackPressed();
                }
            }
        });
    }
}
