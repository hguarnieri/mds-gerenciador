package br.ufscar.mds.gerenciador;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import br.ufscar.mds.gerenciador.data.Nota;
import br.ufscar.mds.gerenciador.database.DbInterface;
import br.ufscar.mds.gerenciador.utils.GridViewAdapter;

/**
 * Created by gabri on 12/02/2017.
 */

public class NotesActivity extends AppCompatActivity {
    private String TAG = NotesActivity.class.getSimpleName();

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private int mCourseId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mCourseId = intent.getIntExtra("course-id",0);
        setContentView(R.layout.activity_notes);
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.notes_grid_item, getData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"Item selected:" + position);
            }
        });
    }

    private List<Nota> getData() {
        List<Nota> imageItems;
        imageItems = DbInterface.getImages(getApplicationContext(),mCourseId,null);
        return imageItems;
    }
}
