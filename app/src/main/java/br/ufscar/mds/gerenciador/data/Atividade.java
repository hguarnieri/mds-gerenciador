package br.ufscar.mds.gerenciador.data;

import java.util.Date;

/**
 * Created by gabri on 29/01/2017.
 */

public class Atividade {
    private int id;
    private int cursoId;
    private String titulo;
    private String detalhes;
    private Date data;

    public Atividade() {
    }

    public Atividade(int id, int cursoId, String titulo, String detalhes, Date data) {
        this.id = id;
        this.cursoId = cursoId;
        this.titulo = titulo;
        this.detalhes = detalhes;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
