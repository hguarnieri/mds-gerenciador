package br.ufscar.mds.gerenciador.database;

import android.provider.BaseColumns;

/**
 * Created by Henrique on 14/01/2017.
 */

public class GerenciadorContract {

    public static class SemestreEntry implements BaseColumns {
        public static final String TABLE_NAME = "semestres";
        public static final String COLUMN_NAME_NOME = "nome";
        public static final String COLUMN_NAME_ANO = "ano";
        public static final String COLUMN_NAME_PERIODO = "periodo";
    }

    public static class CursoEntry implements BaseColumns {
        public static final String TABLE_NAME = "cursos";
        public static final String COLUMN_NAME_SEMESTRE_ID = "semestre_id";
        public static final String COLUMN_NAME_NOME = "nome";
        public static final String COLUMN_NAME_HORARIO1 = "horario1";
        public static final String COLUMN_NAME_HORARIO2 = "horario2";
        public static final String COLUMN_NAME_ABSENCES = "absences";
    }

    public static class AtividadeEntry implements BaseColumns {
        public static final String TABLE_NAME = "atividades";
        public static final String COLUMN_NAME_CURSO_ID = "curso_id";
        public static final String COLUMN_NAME_TITULO = "titulo";
        public static final String COLUMN_NAME_DETALHES = "detalhes";
        public static final String COLUMN_NAME_DATA = "data";
    }

    public static class ImageEntry implements BaseColumns {
        public static final String TABLE_NAME = "imagens";
        public static final String COLUMN_NAME_CAMINHO = "caminho";
        public static final String COLUMN_NAME_CURSO_ID = "curso_id";
    }

}
