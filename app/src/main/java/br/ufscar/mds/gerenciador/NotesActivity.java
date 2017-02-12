package br.ufscar.mds.gerenciador;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
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
    List<Nota> imageItems;

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
                Nota nota = imageItems.get(position);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + nota.getCaminho()), "image/*");
                startActivity(intent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Nota nota = imageItems.get(position);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(nota.getCaminho()));
                startActivity(Intent.createChooser(share, "Compartilhar"));

                return true;
            }
        });
    }

    private List<Nota> getData() {
        imageItems = DbInterface.getImages(getApplicationContext(),mCourseId,null);
        return imageItems;
    }
}
