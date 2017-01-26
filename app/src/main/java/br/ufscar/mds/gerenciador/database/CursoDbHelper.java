package br.ufscar.mds.gerenciador.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gabri on 22/01/2017.
 */

public class CursoDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String NOT_NULL_ENTRY = " NOT NULL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GerenciadorContract.CursoEntry.TABLE_NAME + " (" +
                    GerenciadorContract.CursoEntry._ID + " INTEGER PRIMARY KEY," +
                    GerenciadorContract.CursoEntry.COLUMN_NAME_ID + INTEGER_TYPE + " AUTOINCREMENT" + COMMA_SEP +
                    GerenciadorContract.CursoEntry.COLUMN_NAME_NOME + TEXT_TYPE + NOT_NULL_ENTRY + COMMA_SEP +
                    GerenciadorContract.CursoEntry.COLUMN_NAME_HORARIO1 + TEXT_TYPE + COMMA_SEP +
                    GerenciadorContract.CursoEntry.COLUMN_NAME_HORARIO2 + TEXT_TYPE + COMMA_SEP + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GerenciadorContract.CursoEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Cursos.db";

    public CursoDbHelper(Context context) {
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
