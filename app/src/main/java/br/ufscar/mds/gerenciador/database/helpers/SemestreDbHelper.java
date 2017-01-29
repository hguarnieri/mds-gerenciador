package br.ufscar.mds.gerenciador.database.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.ufscar.mds.gerenciador.database.GerenciadorContract;

/**
 * Created by Henrique on 14/01/2017.
 */

public class SemestreDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String NOT_NULL_ENTRY = " NOT NULL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GerenciadorContract.SemestreEntry.TABLE_NAME + " (" +
                    GerenciadorContract.SemestreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GerenciadorContract.SemestreEntry.COLUMN_NAME_NOME + TEXT_TYPE + NOT_NULL_ENTRY + COMMA_SEP +
                    GerenciadorContract.SemestreEntry.COLUMN_NAME_ANO + TEXT_TYPE + COMMA_SEP +
                    GerenciadorContract.SemestreEntry.COLUMN_NAME_PERIODO + TEXT_TYPE + COMMA_SEP + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GerenciadorContract.SemestreEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Semestres.db";

    public SemestreDbHelper(Context context) {
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
