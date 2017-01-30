package br.ufscar.mds.gerenciador.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ufscar.mds.gerenciador.data.Atividade;
import br.ufscar.mds.gerenciador.data.Curso;
import br.ufscar.mds.gerenciador.data.Nota;
import br.ufscar.mds.gerenciador.data.Semestre;
import br.ufscar.mds.gerenciador.database.helpers.AtividadeDbHelper;
import br.ufscar.mds.gerenciador.database.helpers.CursoDbHelper;
import br.ufscar.mds.gerenciador.database.helpers.ImageDbHelper;
import br.ufscar.mds.gerenciador.database.helpers.SemestreDbHelper;

/**
 * Created by gabri on 29/01/2017.
 */

public class DbInterface {

    // Função de salvamento do semestre
    public static void saveSemester(Context context, Semestre semestre) {
        ContentValues values = new ContentValues();
        SemestreDbHelper dbHelper = new SemestreDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        values.put(GerenciadorContract.SemestreEntry.COLUMN_NAME_NOME, semestre.getNome());
        values.put(GerenciadorContract.SemestreEntry.COLUMN_NAME_ANO, Integer.toString(semestre.getAno()));
        values.put(GerenciadorContract.SemestreEntry.COLUMN_NAME_PERIODO, Integer.toString(semestre.getPeriodo()));

        long newRowId = db.insert(GerenciadorContract.SemestreEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Log.d("Gerenciador", "Erro ao salvar Semestre " + semestre.toString());
        } else {
            Log.d("Gerenciador", "Semestre salvo! " + semestre.toString());
            // TODO: Adicionar à lista
            // Intent i = new Intent("refreshMainActivity"); // TODO: APLICAR O BROADCAST
            // context.sendBroadcast(i);
        }
    }

    // Função de update do semestre
    public static void updateSemester(Context context, Semestre semestre) {
        ContentValues values      = new ContentValues();
        SemestreDbHelper dbHelper = new SemestreDbHelper(context);
        SQLiteDatabase db         = dbHelper.getReadableDatabase();

        values.put(GerenciadorContract.SemestreEntry.COLUMN_NAME_NOME,    semestre.getNome());
        values.put(GerenciadorContract.SemestreEntry.COLUMN_NAME_ANO,     Integer.toString(semestre.getAno()));
        values.put(GerenciadorContract.SemestreEntry.COLUMN_NAME_PERIODO, Integer.toString(semestre.getPeriodo()));

        // Parameters
        String   whereClause = GerenciadorContract.SemestreEntry._ID + " = ?";
        String[] whereArgs   = new String[]{ Integer.toString(semestre.getId())};

        long rowsAffected = db.update(GerenciadorContract.SemestreEntry.TABLE_NAME, values, whereClause, whereArgs);

        if (rowsAffected > 1 || rowsAffected == 0) {
            Log.d("Gerenciador", "Erro ao modificar Semestre " + semestre.toString());
        } else {
            Log.d("Gerenciador", "Semestre modificado! " + semestre.toString());
            // TODO: Adicionar à lista
            // Intent i = new Intent("refreshMainActivity"); // TODO: APLICAR O BROADCAST
            // context.sendBroadcast(i);
        }
    }

    // Função de salvamento do curso
    public static void saveCourse(Context context, Curso course) {
        ContentValues values      = new ContentValues();
        CursoDbHelper dbHelper = new CursoDbHelper(context);
        SQLiteDatabase db         = dbHelper.getReadableDatabase();

        values.put(GerenciadorContract.CursoEntry.COLUMN_NAME_SEMESTRE_ID, course.getSemestreId());
        values.put(GerenciadorContract.CursoEntry.COLUMN_NAME_NOME, course.getNome());
        values.put(GerenciadorContract.CursoEntry.COLUMN_NAME_HORARIO1, course.getHorario1());
        values.put(GerenciadorContract.CursoEntry.COLUMN_NAME_HORARIO2, course.getHorario2());
        values.put(GerenciadorContract.CursoEntry.COLUMN_NAME_ABSENCES, course.getAbsences());

        long newRowId = db.insert(GerenciadorContract.CursoEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Log.d("Gerenciador", "Erro ao salvar curso " + course.toString());
        } else {
            Log.d("Gerenciador", "Curso salvo! " + course.toString());
            // TODO: Adicionar à lista
            // Intent i = new Intent("refreshMainActivity"); // TODO: APLICAR O BROADCAST
            // context.sendBroadcast(i);
        }
    }

    // Função de salvamento do curso
    public static void updateCourse(Context context, Curso course) {
        ContentValues values      = new ContentValues();
        CursoDbHelper dbHelper = new CursoDbHelper(context);
        SQLiteDatabase db         = dbHelper.getReadableDatabase();

        values.put(GerenciadorContract.CursoEntry.COLUMN_NAME_SEMESTRE_ID, course.getSemestreId());
        values.put(GerenciadorContract.CursoEntry.COLUMN_NAME_NOME, course.getNome());
        values.put(GerenciadorContract.CursoEntry.COLUMN_NAME_HORARIO1, course.getHorario1());
        values.put(GerenciadorContract.CursoEntry.COLUMN_NAME_HORARIO2, course.getHorario2());
        values.put(GerenciadorContract.CursoEntry.COLUMN_NAME_ABSENCES, course.getAbsences());

        // Parameters
        String   whereClause = GerenciadorContract.CursoEntry._ID + " = ?";
        String[] whereArgs   = new String[]{ Integer.toString(course.getId())};

        long rowsAffected = db.update(GerenciadorContract.CursoEntry.TABLE_NAME, values, whereClause, whereArgs);

        if (rowsAffected > 1 || rowsAffected == 0) {
            Log.d("Gerenciador", "Erro ao modificar Semestre " + course.toString());
        } else {
            Log.d("Gerenciador", "Semestre modificado! " + course.toString());
            // TODO: Adicionar à lista
            // Intent i = new Intent("refreshMainActivity"); // TODO: APLICAR O BROADCAST
            // context.sendBroadcast(i);
        }
    }

    public static long saveAssignment(Context context, Atividade atividade) {
        ContentValues values       = new ContentValues();
        AtividadeDbHelper dbHelper = new AtividadeDbHelper(context);
        SQLiteDatabase db          = dbHelper.getReadableDatabase();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        values.put(GerenciadorContract.AtividadeEntry.COLUMN_NAME_CURSO_ID, atividade.getCursoId());
        values.put(GerenciadorContract.AtividadeEntry.COLUMN_NAME_TITULO, atividade.getTitulo());
        values.put(GerenciadorContract.AtividadeEntry.COLUMN_NAME_DETALHES, atividade.getDetalhes());
        values.put(GerenciadorContract.AtividadeEntry.COLUMN_NAME_DATA, df.format(atividade.getData()));

        long newRowId = db.insert(GerenciadorContract.AtividadeEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Log.d("Gerenciador", "Erro ao salvar atividade " + atividade.toString());
        } else {
            Log.d("Gerenciador", "Atividade salva! " + atividade.toString());
            Intent i = new Intent("refreshAssignment");
            context.sendBroadcast(i);
        }

        return newRowId;
    }

    public static void updateAssignment(Context context, Atividade atividade) {
        ContentValues values       = new ContentValues();
        AtividadeDbHelper dbHelper = new AtividadeDbHelper(context);
        SQLiteDatabase db          = dbHelper.getReadableDatabase();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        values.put(GerenciadorContract.AtividadeEntry.COLUMN_NAME_CURSO_ID, atividade.getCursoId());
        values.put(GerenciadorContract.AtividadeEntry.COLUMN_NAME_TITULO, atividade.getTitulo());
        values.put(GerenciadorContract.AtividadeEntry.COLUMN_NAME_DETALHES, atividade.getDetalhes());
        values.put(GerenciadorContract.AtividadeEntry.COLUMN_NAME_DATA, df.format(atividade.getData()));

        // Parameters
        String   whereClause = GerenciadorContract.AtividadeEntry._ID + " = ?";
        String[] whereArgs   = new String[]{ Integer.toString(atividade.getId())};

        long rowsAffected = db.update(GerenciadorContract.AtividadeEntry.TABLE_NAME, values, whereClause, whereArgs);

        if (rowsAffected > 1 || rowsAffected == 0) {
            Log.d("Gerenciador", "Erro ao salvar atividade " + atividade.toString());
        } else {
            Log.d("Gerenciador", "Atividade salva! " + atividade.toString());
            // TODO: Adicionar à lista
            // Intent i = new Intent("refreshMainActivity"); // TODO: APLICAR O BROADCAST
            // context.sendBroadcast(i);
        }
    }

    public static void deleteSemester(Context context, Semestre semester) {
        SemestreDbHelper dbHelper = new SemestreDbHelper(context);
        SQLiteDatabase db          = dbHelper.getReadableDatabase();

        // Parameters
        String   whereClause = GerenciadorContract.SemestreEntry._ID + " = ?";
        String[] whereArgs   = new String[]{ Integer.toString(semester.getId())};

        db.delete(GerenciadorContract.SemestreEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public static void deleteCourse(Context context, Curso curso) {
        CursoDbHelper dbHelper = new CursoDbHelper(context);
        SQLiteDatabase db          = dbHelper.getReadableDatabase();

        // Parameters
        String   whereClause = GerenciadorContract.CursoEntry._ID + " = ?";
        String[] whereArgs   = new String[]{ Integer.toString(curso.getId())};

        db.delete(GerenciadorContract.CursoEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public static void deleteAssignment(Context context, Atividade assignment) {
        AtividadeDbHelper dbHelper = new AtividadeDbHelper(context);
        SQLiteDatabase db          = dbHelper.getReadableDatabase();

        // Parameters
        String   whereClause = GerenciadorContract.AtividadeEntry._ID + " = ?";
        String[] whereArgs   = new String[]{ Integer.toString(assignment.getId())};

        db.delete(GerenciadorContract.AtividadeEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public static Semestre getSemester(Context context, int ano, int periodo) {
        List<Semestre> semestres = getSemesters(context, ano, periodo);

        if (semestres.size() == 1) { // Só pode existir 1 semestre
            return semestres.get(0);
        } else if (semestres.size() > 1) { // Se mais, retornar erro
            System.out.println("Encontrado mais de um semestre com o mesmo ano e período");
            return null;
        } else { // Se não existir, nulo
            return null;
        }
    }

    public static List<Semestre> getAllSemesters(Context context) {
        return getSemesters(context, null, null);
    }

    private static List<Semestre> getSemesters(Context context, Integer ano, Integer periodo) {
        List<Semestre> lista = new ArrayList<Semestre>();

        SemestreDbHelper dbHelper;
        dbHelper = new SemestreDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] tableColumns = {
                GerenciadorContract.SemestreEntry._ID,
                GerenciadorContract.SemestreEntry.COLUMN_NAME_NOME,
                GerenciadorContract.SemestreEntry.COLUMN_NAME_ANO,
                GerenciadorContract.SemestreEntry.COLUMN_NAME_PERIODO
        };

        // Parameters
        String   whereClause = null;
        String[] whereArgs = null;
        String   sortOrder = GerenciadorContract.SemestreEntry.COLUMN_NAME_ANO + ", "
                             + GerenciadorContract.SemestreEntry.COLUMN_NAME_PERIODO + " ASC";

        if (ano != null && periodo != null) {
            whereArgs   = new String[]{ Integer.toString(ano), Integer.toString(periodo) };
            whereClause = GerenciadorContract.SemestreEntry.COLUMN_NAME_ANO + " = ? AND "
                        + GerenciadorContract.SemestreEntry.COLUMN_NAME_PERIODO + " = ?";
        } else if (ano != null) {
            whereArgs   = new String[]{ Integer.toString(ano) };
            whereClause = GerenciadorContract.SemestreEntry.COLUMN_NAME_ANO + " = ?";
        } else if (periodo != null) {
            whereArgs   = new String[]{ Integer.toString(periodo) };
            whereClause = GerenciadorContract.SemestreEntry.COLUMN_NAME_PERIODO + " = ?";
        }

        Cursor cursor = db.query(
                GerenciadorContract.SemestreEntry.TABLE_NAME,
                tableColumns,
                whereClause,
                whereArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()){
            lista.add(cursorToSemestre(cursor));
        }

        dbHelper.close();

        return lista;
    }

    public static List<Curso> getAllCourses(Context context) {
        return getAllCoursesBySemester(context, null);
    }

    public static List<Curso> getAllCoursesBySemester(Context context, Integer semesterId) {
        return getCourses(context, semesterId, null);
    }

    public static Curso getCourse(Context context, int courseId) {
        return getCourses(context, null, courseId).get(0);
    }

    // Vamos manter privado para evitar erros, utilizar os métodos acima ou criar novos
    private static List<Curso> getCourses(Context context, Integer semesterId, Integer courseId) {
        if (semesterId != null && courseId != null) {
            System.out.println("Não é possível utilizar getCourses com semesterId e courseId preenchidos, " +
                               "defina um como null");
            return new ArrayList<Curso>();
        }

        List<Curso> lista = new ArrayList<Curso>();

        CursoDbHelper dbHelper;
        dbHelper = new CursoDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] tableColumns = {
                GerenciadorContract.CursoEntry._ID,
                GerenciadorContract.CursoEntry.COLUMN_NAME_SEMESTRE_ID,
                GerenciadorContract.CursoEntry.COLUMN_NAME_NOME,
                GerenciadorContract.CursoEntry.COLUMN_NAME_HORARIO1,
                GerenciadorContract.CursoEntry.COLUMN_NAME_HORARIO2,
                GerenciadorContract.CursoEntry.COLUMN_NAME_ABSENCES
        };

        // Parameters
        String   whereClause = null;
        String[] whereArgs = null;
        String   sortOrder = GerenciadorContract.CursoEntry.COLUMN_NAME_NOME + " ASC";

        if (semesterId != null) {
            whereClause = GerenciadorContract.CursoEntry.COLUMN_NAME_SEMESTRE_ID + " = ?";
            whereArgs   = new String[]{ Integer.toString(semesterId) };
        }

        if (courseId != null) {
            whereClause = GerenciadorContract.CursoEntry._ID + " = ?";
            whereArgs   = new String[]{ Integer.toString(courseId) };
        }

        Cursor cursor = db.query(
                GerenciadorContract.CursoEntry.TABLE_NAME,
                tableColumns,
                whereClause,
                whereArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()){
            lista.add(cursorToCurso(cursor));
        }

        dbHelper.close();

        return lista;
    }

    public static Atividade getAssignment(Context context, int assignmentId) {
        List<Atividade> atividades = getAssignments(context, null, null, null, assignmentId);

        if (atividades.size() == 1) {
            return atividades.get(0);
        } else if (atividades.size() >= 1) {
            System.out.println("Erro! Mais de um assignment com o mesmo id encontrado!");
            return null;
        } else {
            return null;
        }
    }

    public static List<Atividade> getAllFutureAssignments(Context context) {
        List<Atividade> atividadeList = getAllFutureAssignmentsByCourse(context, null);

        return atividadeList;
    }

    public static List<Atividade> getAllFutureAssignmentsByCourse(Context context, Integer courseId) {
        Calendar futureCalendar = Calendar.getInstance();
        futureCalendar.add(Calendar.YEAR, 10); // Incrementa 10 anos

        List<Atividade> atividadeList = getAssignments(context, courseId, new Date(), futureCalendar.getTime(), null);

        return atividadeList;
    }

    private static List<Atividade> getAssignments(Context context, Integer courseId,
                                                Date initialDate, Date finalDate, Integer assignmentId) {
        List<Atividade> lista = new ArrayList<Atividade>();

        AtividadeDbHelper dbHelper;
        dbHelper = new AtividadeDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String[] tableColumns = {
                GerenciadorContract.AtividadeEntry._ID,
                GerenciadorContract.AtividadeEntry.COLUMN_NAME_TITULO,
                GerenciadorContract.AtividadeEntry.COLUMN_NAME_DATA,
                GerenciadorContract.AtividadeEntry.COLUMN_NAME_CURSO_ID,
                GerenciadorContract.AtividadeEntry.COLUMN_NAME_DETALHES
        };

        // Parameters
        String   whereClause = null;
        String[] whereArgs = null;
        String sortOrder = GerenciadorContract.AtividadeEntry.COLUMN_NAME_DATA + " ASC";

        // Aqui funciona assim:
        // 1: Assignment específico
        // 2: Caso contrário, busca por:
        // 2.1: Assignments de um curso com intervalo de datas (courseId, initialDate, finalDate)
        // 2.2: Assignments de um curso com id específico (courseId)
        // 2.3: Assignments de um intervalo de datas (initialDate e finalDate)
        if (assignmentId != null) {
            whereClause = GerenciadorContract.AtividadeEntry._ID + " = ?";
            whereArgs   = new String[]{ Integer.toString(assignmentId) };
        } else if (courseId != null && initialDate != null && finalDate != null) {
            whereClause = GerenciadorContract.AtividadeEntry.COLUMN_NAME_CURSO_ID + " = ? AND "
                        + GerenciadorContract.AtividadeEntry.COLUMN_NAME_DATA + " BETWEEN ? AND ?";
            whereArgs   = new String[]{ Integer.toString(courseId), df.format(initialDate), df.format(finalDate) };
        } else if (courseId != null) {
            whereClause = GerenciadorContract.AtividadeEntry.COLUMN_NAME_CURSO_ID + " = ?";
            whereArgs   = new String[]{ Integer.toString(courseId) };
        } else if (initialDate != null && finalDate != null) {
            whereClause = GerenciadorContract.AtividadeEntry.COLUMN_NAME_DATA + " BETWEEN ? AND ?";
            whereArgs   = new String[]{ df.format(initialDate), df.format(finalDate) };
        }

        Cursor cursor = db.query(
                GerenciadorContract.AtividadeEntry.TABLE_NAME,
                tableColumns,
                whereClause,
                whereArgs,
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

    private static Atividade cursorToAtividade(Cursor cursor){
        Atividade atividade = new Atividade();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        atividade.setId(cursor.getInt(0));
        atividade.setTitulo(cursor.getString(1));
        try {
            atividade.setData(sdf.parse(cursor.getString(2)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        atividade.setCursoId(cursor.getInt(3));
        atividade.setDetalhes(cursor.getString(4));

        return atividade;
    }

    private static Semestre cursorToSemestre(Cursor cursor){
        Semestre semestre = new Semestre();

        semestre.setId(cursor.getInt(0));
        semestre.setNome(cursor.getString(1));
        semestre.setAno(cursor.getInt(2));
        semestre.setPeriodo(cursor.getInt(3));

        return semestre;
    }

    private static Curso cursorToCurso(Cursor cursor){
        Curso curso = new Curso();

        curso.setId(cursor.getInt(0));
        curso.setSemestreId(cursor.getInt(1));
        curso.setNome(cursor.getString(2));
        curso.setHorario1(cursor.getString(3));
        curso.setHorario2(cursor.getString(4));
        curso.setAbsences(cursor.getInt(5));

        return curso;
    }

    public static Nota getImage(Context context, int imageId) {
        List<Nota> notas = getImages(context, null, imageId);

        if (notas.size() == 1) {
            return notas.get(0);
        } else if (notas.size() >= 1) {
            System.out.println("Erro! Mais de um assignment com o mesmo id encontrado!");
            return null;
        } else {
            return null;
        }
    }

    private static List<Nota> getImages(Context context, Integer courseId, Integer imageId) {
        List<Nota> lista = new ArrayList<Nota>();

        ImageDbHelper dbHelper;
        dbHelper = new ImageDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] tableColumns = {
                GerenciadorContract.ImageEntry._ID,
                GerenciadorContract.ImageEntry.COLUMN_NAME_CAMINHO,
                GerenciadorContract.ImageEntry.COLUMN_NAME_CURSO_ID
        };

        // Parameters
        String   whereClause = null;
        String[] whereArgs = null;
        String sortOrder = GerenciadorContract.ImageEntry.COLUMN_NAME_CURSO_ID + " ASC";

        // Aqui funciona assim:
        // 1: Assignment específico
        // 2: Caso contrário, busca por:
        // 2.1: Assignments de um curso com intervalo de datas (courseId, initialDate, finalDate)
        // 2.2: Assignments de um curso com id específico (courseId)
        // 2.3: Assignments de um intervalo de datas (initialDate e finalDate)
        if (imageId != null) {
            whereClause = GerenciadorContract.ImageEntry._ID + " = ?";
            whereArgs   = new String[]{ Integer.toString(imageId) };
        } else if (courseId != null) {
            whereClause = GerenciadorContract.ImageEntry.COLUMN_NAME_CURSO_ID + " = ?";
            whereArgs   = new String[]{ Integer.toString(courseId) };
        }

        Cursor cursor = db.query(
                GerenciadorContract.ImageEntry.TABLE_NAME,
                tableColumns,
                whereClause,
                whereArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()){
            lista.add(cursorToNota(cursor));
        }

        dbHelper.close();

        return lista;
    }

    private static Nota cursorToNota(Cursor cursor){
        Nota nota = new Nota();

        nota.setId(cursor.getInt(0));
        nota.setCaminho(cursor.getString(1));
        nota.setCursoId(cursor.getInt(2));

        return nota;
    }

    public static void saveImage(Context context, Nota note) {
        ContentValues values       = new ContentValues();
        ImageDbHelper dbHelper = new ImageDbHelper(context);
        SQLiteDatabase db          = dbHelper.getReadableDatabase();

        values.put(GerenciadorContract.ImageEntry.COLUMN_NAME_CURSO_ID, note.getCursoId());
        values.put(GerenciadorContract.ImageEntry.COLUMN_NAME_CAMINHO, note.getCaminho());

        long newRowId = db.insert(GerenciadorContract.ImageEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Log.d("Gerenciador", "Erro ao salvar nota " + note.toString());
        } else {
            Log.d("Gerenciador", "Nota salva! " + note.toString());
            // TODO: Adicionar à lista
            // Intent i = new Intent("refreshMainActivity"); // TODO: APLICAR O BROADCAST
            // context.sendBroadcast(i);
        }
    }

    public static void updateImage(Context context, Nota note) {
        ContentValues values       = new ContentValues();
        AtividadeDbHelper dbHelper = new AtividadeDbHelper(context);
        SQLiteDatabase db          = dbHelper.getReadableDatabase();

        values.put(GerenciadorContract.ImageEntry.COLUMN_NAME_CURSO_ID, note.getCursoId());
        values.put(GerenciadorContract.ImageEntry.COLUMN_NAME_CAMINHO, note.getCaminho());

        // Parameters
        String   whereClause = GerenciadorContract.AtividadeEntry._ID + " = ?";
        String[] whereArgs   = new String[]{ Integer.toString(note.getId())};

        long rowsAffected = db.update(GerenciadorContract.ImageEntry.TABLE_NAME, values, whereClause, whereArgs);

        if (rowsAffected > 1 || rowsAffected == 0) {
            Log.d("Gerenciador", "Erro ao salvar nota " + note.toString());
        } else {
            Log.d("Gerenciador", "Nota salva! " + note.toString());
            // TODO: Adicionar à lista
            // Intent i = new Intent("refreshMainActivity"); // TODO: APLICAR O BROADCAST
            // context.sendBroadcast(i);
        }
    }

    public static void deleteImage(Context context, Nota note) {
        AtividadeDbHelper dbHelper = new AtividadeDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Parameters
        String whereClause = GerenciadorContract.ImageEntry._ID + " = ?";
        String[] whereArgs = new String[]{Integer.toString(note.getId())};

        db.delete(GerenciadorContract.ImageEntry.TABLE_NAME, whereClause, whereArgs);
    }
}
