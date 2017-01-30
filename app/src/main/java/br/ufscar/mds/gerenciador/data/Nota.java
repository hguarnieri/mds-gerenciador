package br.ufscar.mds.gerenciador.data;

/**
 * Created by gabri on 29/01/2017.
 */

public class Nota {
    private int id;
    private String caminho;
    private int cursoId;

    public Nota() {
    }

    public Nota(int id, String caminho, int cursoId) {
        this.id = id;
        this.caminho = caminho;
        this.cursoId = cursoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }
}
