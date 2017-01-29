package br.ufscar.mds.gerenciador.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.ufscar.mds.gerenciador.data.Atividade;

/**
 * Created by gabri on 29/01/2017.
 */

public class DbInterface {

    public Atividade cursorToAtividade(Cursor cursor){
        Atividade atividade = new Atividade();
        atividade.setId(cursor.getInt(0));
        atividade.setTitulo(cursor.getString(1));
        atividade.setData(cursor.getString(2));
        atividade.setCursoId(cursor.getInt(3));
        atividade.setDetalhes(cursor.getString(4));

        return atividade;
    }

    public List<Atividade> getAtividades(Context context){
        List<Atividade> lista = new ArrayList<Atividade>();
        AtividadeDbHelper dbHelper;
        dbHelper = new AtividadeDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                GerenciadorContract.AtividadeEntry._ID,
                GerenciadorContract.AtividadeEntry.COLUMN_NAME_TITULO,
                GerenciadorContract.AtividadeEntry.COLUMN_NAME_DATA,
                GerenciadorContract.AtividadeEntry.COLUMN_NAME_CURSO_ID,
                GerenciadorContract.AtividadeEntry.COLUMN_NAME_DETALHES
        };
        String sortOrder = GerenciadorContract.AtividadeEntry.COLUMN_NAME_DATA + "DESC";
        Cursor cursor = db.query(
                GerenciadorContract.AtividadeEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        while (cursor.moveToNext()){
            lista.add(cursorToAtividade(cursor));
        }
        dbHelper.close();
        return lista;
    }

}
