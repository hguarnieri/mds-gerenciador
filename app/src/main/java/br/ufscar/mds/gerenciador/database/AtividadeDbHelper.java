package br.ufscar.mds.gerenciador.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gabri on 22/01/2017.
 */

public class AtividadeDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String NOT_NULL_ENTRY = " NOT NULL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GerenciadorContract.AtividadeEntry.TABLE_NAME + " (" +
                    GerenciadorContract.AtividadeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GerenciadorContract.AtividadeEntry.COLUMN_NAME_CURSO_ID + INTEGER_TYPE + COMMA_SEP +
                    GerenciadorContract.AtividadeEntry.COLUMN_NAME_TITULO + TEXT_TYPE + NOT_NULL_ENTRY + COMMA_SEP +
                    GerenciadorContract.AtividadeEntry.COLUMN_NAME_DETALHES + TEXT_TYPE + COMMA_SEP +
                    GerenciadorContract.AtividadeEntry.COLUMN_NAME_DATA + TEXT_TYPE + COMMA_SEP +
                    "FOREIGN KEY(" + GerenciadorContract.AtividadeEntry.COLUMN_NAME_CURSO_ID + ") REFERENCES " +
                            GerenciadorContract.CursoEntry.TABLE_NAME + "(" + GerenciadorContract.CursoEntry._ID + ") )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GerenciadorContract.AtividadeEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Atividades.db";

    public AtividadeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
