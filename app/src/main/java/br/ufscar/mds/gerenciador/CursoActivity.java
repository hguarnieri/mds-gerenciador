package br.ufscar.mds.gerenciador;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufscar.mds.gerenciador.data.Semestre;
import br.ufscar.mds.gerenciador.database.DbInterface;

/**
 * Created by gabri on 29/01/2017.
 */

public class CursoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);


    }
}
